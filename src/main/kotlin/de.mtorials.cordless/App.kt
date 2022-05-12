package de.mtorials.cordless

import de.mtorials.cordless.components.messagesTemplate
import de.mtorials.cordless.persistence.PersitenceManager
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.entities.room.JoinedRoomImpl
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.kompore.components.*
import de.mtorials.kompore.state.Property
import de.mtorials.kompore.styling.fullHeight
import de.mtorials.kompore.styling.fullWidth
import de.mtorials.kompore.styling.isSecondary
import de.mtorials.kompore.templates.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlinx.css.CSSBuilder
import kotlinx.css.label
import kotlinx.html.dom.create
import kotlinx.html.js.style
import kotlinx.html.unsafe

class App {

  private val persistence = PersitenceManager()

  private val state : CordlessState = CordlessState(
    Property(RoomId("")) {
      // messageComponent.set(messageListener.getMessages(it).toMutableList());
      persistence.setActiveRoom(it)
    }
  )
  private var phone : DialPhone? = null

  private val messageListener = ListenerAdapter {
    onRoomMessageReceived list@{
      if (state.activeRoomId.value != it.room.id) return@list
      println("${it.message.author.id} :: ${it.message.content.body}")
      messageComponent.update { add(it.message) }
    }
  }

  private val messageComponent = messagesTemplate(mutableListOf())

  suspend fun main() {

    phone = DialPhone("https://matrix.mtorials.de") {
      withToken(persistence.getToken() ?: handleLogin())
      addListeners(
        messageListener
      )
    }

    state.activeRoomId.value = persistence.getActiveRoom()

    val roomListTemplate = ReactiveComponent<List<JoinedRoom>> {
      name = "roomList"
      addClass("container")
      fullHeight()
      it.forEach { room ->
        if (state.activeRoomId.value == room.id) buttonWarning(room.name ?: room.members[0].displayName ?: room.members[0].id.toString() ) {}
        else buttonPrimary(room.name ?: "-") {
          state.activeRoomId.value = room.id
        }
      }
    }

    val rooms = (phone!!.getJoinedRooms().toMutableList())
    val invites = phone!!.getInvitedRoomActions()

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
              reactive(invites) { invites ->
                invites.forEach { room ->
                  text("Invite:")
                  button(room.name ?: "-") {
                    MainScope().launch { room.join() }
                  }
                }
              }
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
                button(member.id.toString()) {}
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
      phone!!.getJoinedRoomById(state.activeRoomId.value)?.sendTextMessage(inputBinding.value)
      inputBinding.value = ""
    }
  }
}

suspend fun main() {
  App().main()
}