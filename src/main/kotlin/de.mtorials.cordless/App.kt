package de.mtorials.cordless

import de.mtorials.cordless.components.messagesTemplate
import de.mtorials.cordless.persistence.PersitenceManager
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.dialphone.sendTextMessage
import de.mtorials.kompore.components.*
import de.mtorials.kompore.state.Property
import de.mtorials.kompore.styling.fullHeight
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

  private val persistence = PersitenceManager()

  private val state = CordlessState(
    Property("") { messageComponent.set(messageListener.getMessages(it).toMutableList()); persistence.setActiveRoom(it) }
  )
  private var phone : DialPhone? = null

  private val messageListener: MessageListener = MessageListener(receiveOld = true) {
    if (state.activeRoomId.value != it.roomFuture.id) return@MessageListener
    println("${it.message.author.id} :: ${it.message.body}")
    messageComponent.update { add(it.message) }
  }
  private val messageComponent = messagesTemplate(mutableListOf())

  suspend fun main() {

    phone = DialPhone {
      homeserverUrl = "https://matrix.mtorials.de"
      withToken(persistence.getToken() ?: handleLogin())
      addListeners(
        messageListener
      )
    }

    state.activeRoomId.value = persistence.getActiveRoom()

    val roomListTemplate = ReactiveComponent<List<Room>> {
      name = "roomList"
      addClass("container")
      fullHeight()
      it.forEach { room ->
        if (state.activeRoomId.value == room.id) buttonWarning(room.name) {}
        else buttonPrimary(room.name) {
          state.activeRoomId.value = room.id
        }
      }
    }

    val rooms = (phone!!.getJoinedRoomFutures().map { it.receive() }).toMutableList()

    val root = Component.root("cordless") {
      boxVertical {
        fullWidth()

        containerBackground {
          boxHorizontal {
            container {
              heading("Hello ${phone!!.ownId}")
            }
            container {
              text("This is where the voice channels will go!")
            }
            container {
              text("This is space for something else")
            }
          }
        }

        boxHorizontal {
          fullWidth()

          addComponent(roomListTemplate(rooms).hookOnProperty(state.activeRoomId))
          containerBackground {
            fullWidth()
            addComponent(messageComponent)
            boxHorizontal {
              val inputBinding = Property("") { println("typing") }
              textArea(inputBinding, true) {
                isSecondary()
                fullWidth()
                onEnter { sendMessage(phone!!, inputBinding) }
              }
              buttonPrimary("send") {
                sendMessage(phone!!, inputBinding)
              }
            }
          }
          containerBackground {
            reactive(null) {
              val room = rooms.filter { it.id == state.activeRoomId.value }[0]
              boxVertical { room.members.forEach { member ->
                button(member.id) {}
              } }
            }.hookOnProperty(state.activeRoomId)
          }
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

  private fun handleLogin() : String {
    val token = persistence.getToken() ?: window.prompt("please enter your token") ?: error("You need to enter a token")
    persistence.setToken(token)
    return token
  }

  private fun sendMessage(phone: DialPhone, inputBinding: Property<String>) {
    MainScope().promise {
      phone!!.getJoinedRoomFutureById(state.activeRoomId.value)?.sendTextMessage(inputBinding.value)
      inputBinding.value = ""
    }
  }
}

suspend fun main() {
  App().main()
}