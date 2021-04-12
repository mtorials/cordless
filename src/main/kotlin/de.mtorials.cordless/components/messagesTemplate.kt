package de.mtorials.cordless.components

import de.mtorials.dialphone.entities.Message
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.styling.isSecondary
import kotlinx.css.padding

val messagesTemplate = ReactiveComponent<MutableList<Message>> { it.forEach { msg ->
  name = "listcomponent"
  isSecondary()
  msg.run { text("${author.id} :: $body") }
  style {
    padding = "1rem"
  }
} }