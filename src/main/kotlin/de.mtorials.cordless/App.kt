package de.mtorials.cordless

import de.mtorials.cordless.components.messagesTemplate
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Message
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.kompore.components.*
import de.mtorials.kompore.styling.fullWidth
import de.mtorials.kompore.styling.isPrimary
import de.mtorials.kompore.styling.isSecondary
import de.mtorials.kompore.templates.container
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.CSSBuilder
import kotlinx.css.padding
import kotlinx.html.dom.create
import kotlinx.html.js.style
import kotlinx.html.unsafe

suspend fun main() {

  val state = CordlessState("")
  val token = window.prompt("please enter your token") ?: error("You need to enter a token")
  val messageComponent = messagesTemplate(mutableListOf())

  val phone = DialPhone {
    homeserverUrl = "https://matrix.mtorials.de"
    withToken(token)
  }

  val messageListener = MessageListener(receiveOld = true) {
    if (state.activeRoomId != it.roomFuture.id) return@MessageListener
    println("${it.message.author.id} :: ${it.message.body}")
    messageComponent.update { add(it.message) }
  }.also { phone.addListener(it) }

  val roomList = ListComponent<Room> { room ->
    name = "roomList"
    isPrimary()
    text(room.name)
    onClick {
      state.activeRoomId = room.id
      messageComponent.set(messageListener.getMessages(state.activeRoomId).toMutableList())
    }
  }

  roomList.addAll(phone.getJoinedRoomFutures().map { it.receive() })

  val root2 = Component.root("cordless") {
    container {
      heading("cordless")
      addComponent(roomList)
    }
    container {
      isSecondary()
      fullWidth()
      addComponent(messageComponent)
    }
  }

  document.body?.append(root2.element)
  document.head?.append(document.create.style {
    unsafe {
      val builder = CSSBuilder()
      root2.styles.forEach { builder.apply(it) }
      raw(builder.toString())
    }
  })

  phone.sync()
}