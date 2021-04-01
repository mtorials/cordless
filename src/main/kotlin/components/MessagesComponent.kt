package components

import de.mtorials.dialphone.entities.Message
import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ListComponent
import de.mtorials.kompore.components.RunnableStyle
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.dom.clear
import kotlinx.html.a
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

class MessagesComponent(override val name: String) : ListComponent<Message>() {

  override val element: HTMLElement = document.create.div(classes = "messages") {  }
  override val styles: MutableList<RunnableStyle> = mutableListOf()

  override fun update() {
    element.clear()
    forEach { message -> element.appendChild(Component.htmlBuilder(name) {
      a { +message.body }
    }.element) }
  }

  companion object {
    val style = CSSBuilder().apply {
      ".message" {
        backgroundColor = Color.darkGrey
        borderRadius = 10.px
        marginBottom = 1.rem
        padding = "1rem"
      }

      ".messages" {
        padding = "1rem"
        backgroundColor = Color.blue
        display = Display.flex
        flexDirection = FlexDirection.column
      }
    }
  }
}