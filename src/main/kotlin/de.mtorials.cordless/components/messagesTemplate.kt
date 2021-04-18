package de.mtorials.cordless.components

import de.mtorials.dialphone.entities.Message
import de.mtorials.dialphone.entities.Room
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.styling.background
import de.mtorials.kompore.styling.fullHeight
import de.mtorials.kompore.styling.isPrimary
import de.mtorials.kompore.styling.isSecondary
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import kotlinx.css.padding

val messagesTemplate = ReactiveComponent<MutableList<Message>> {
  //if (it.isEmpty()) return@ReactiveComponent
  it.forEach { msg ->
    name = "listcomponent"
    fullHeight()
    background()
    msg.run { para("${author.id} :: $body") }
    style {
      padding = "1rem"
    }
  }
}