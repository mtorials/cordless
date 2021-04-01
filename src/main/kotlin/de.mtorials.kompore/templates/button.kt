package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import kotlinx.css.*
import org.w3c.dom.events.MouseEvent


fun ComponentBuilder.button(label: String, onClick: (MouseEvent) -> Unit) {
  Component("button") {
    text(label)
    style {
      backgroundColor = Color.lightGray
      borderRadius = 10.px
      borderStyle = BorderStyle.solid
      borderWidth = 2.px
      paddingLeft = 1.rem
      paddingTop = 0.5.rem
      paddingBottom = 0.5.rem
    }
    styleHover {
      backgroundColor = Color.gray
    }
    onClick(onClick)
  }().let { this.addComponent(it) }
}