package de.mtorials.cordless

import de.mtorials.cordless.components.messagesTemplate
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.dialphone.sendTextMessage
import de.mtorials.kompore.components.*
import de.mtorials.kompore.state.Property
import de.mtorials.kompore.styling.fullWidth
import de.mtorials.kompore.styling.isSecondary
import de.mtorials.kompore.templates.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlinx.css.CSSBuilder
import kotlinx.html.dom.create
import kotlinx.html.js.style
import kotlinx.html.unsafe

class App {

  private val state = CordlessState(
    Property("") { messageComponent.set(messageListener.getMessages(it).toMutableList()) }
  )
  private var phone : DialPhone? = null

  private val messageComponent = messagesTemplate(mutableListOf())
  private val messageListener : MessageListener = MessageListener(receiveOld = true) {
    if (state.activeRoomId.value != it.roomFuture.id) return@MessageListener
    println("${it.message.author.id} :: ${it.message.body}")
    messageComponent.update { add(it.message) }
  }

  suspend fun main() {
    val token = window.prompt("please enter your token") ?: error("You need to enter a token")

    phone = DialPhone {
      homeserverUrl = "https://matrix.mtorials.de"
      withToken(token)
      addListeners(
        messageListener
      )
    }

    val roomListTemplate = ReactiveComponent<List<Room>> {
      name = "roomList"
      addClass("container")
      it.forEach { room ->
        if (state.activeRoomId.value == room.id) buttonWarning(room.name) {}
        else buttonPrimary(room.name) {
          state.activeRoomId.value = room.id
        }
      }
    }

    val rooms = (phone!!.getJoinedRoomFutures().map { it.receive() }).toMutableList()

    val root = Component.root("cordless") {
      container {
        heading("cordless")
        addComponent(roomListTemplate(rooms).hookOnProperty(state.activeRoomId))
      }
      container {
        isSecondary()
        fullWidth()
        addComponent(messageComponent)
        val inputBinding = Property("test msg") { println("typing") }
        inputText(inputBinding)
        buttonPrimary("Write text hello") {
          MainScope().promise { phone!!.getJoinedRoomFutureById(state.activeRoomId.value)?.sendTextMessage(inputBinding.value) }
          inputBinding.value = ""
        }
      }
    }

    document.body?.append(root.element)
    document.head?.append(document.create.style {
      unsafe {
        val builder = CSSBuilder()
        root.styles.forEach { builder.apply(it) }
        raw(builder.toString())
      }
    })

    phone!!.sync()
  }
}

suspend fun main() {
  App().main()
}