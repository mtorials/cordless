package de.mtorials.cordless.components

import de.mtorials.dialphone.entities.Message
import de.mtorials.dialphone.entities.Room
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.styling.isPrimary
import de.mtorials.kompore.styling.isSecondary
import kotlinx.css.padding

val messagesTemplate = ReactiveComponent<MutableList<Message>> { it.forEach { msg ->
  name = "listcomponent"
  isSecondary()
  msg.run { para("${author.id} :: $body") }
  style {
    padding = "1rem"
  }
} }