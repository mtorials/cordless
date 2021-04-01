package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import org.w3c.dom.events.MouseEvent

fun ComponentBuilder.button(label: String, onClick: (MouseEvent) -> Unit) {
  Component("button") {
    addClass("secondary")
    text(label)
    onClick(onClick)
  }().let { this.addComponent(it) }
}

fun ComponentBuilder.buttonPrimary(label: String, onClick: (MouseEvent) -> Unit) {
  Component("button") {
    addClass("primary")
    text(label)
    onClick(onClick)
  }().let { this.addComponent(it) }
}