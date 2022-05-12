package de.mtorials.cordless.components

import de.mtorials.dialphone.core.entities.Message
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.styling.background
import de.mtorials.kompore.styling.fullHeight
import io.ktor.http.*
import kotlinx.css.padding

val messagesTemplate = ReactiveComponent<MutableList<Message>> {
  //if (it.isEmpty()) return@ReactiveComponent
  it.forEach { msg ->
    name = "listcomponent"
    fullHeight()
    background()
    msg.run { para("${author.id} :: ${content.body}") }
    style {
      padding = "1rem"
    }
  }
}