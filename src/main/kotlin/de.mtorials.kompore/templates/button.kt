package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.components.MutableComponent
import de.mtorials.kompore.components.StandardComponentBuilder
import org.w3c.dom.events.MouseEvent

fun ComponentBuilder<*>.button(label: String, onClick: (MouseEvent) -> Unit) {
  Component("button") {
    addClass("secondary")
    text(label)
    onClick(onClick)
  }().let { this.addComponent(it) }
}

fun ComponentBuilder<*>.buttonPrimary(label: String, onClick: (MouseEvent) -> Unit) {
  Component(this.newName()) {
    addClass("button")
    addClass("primary")
    text(label)
    onClick(onClick)
  }().let { this.addComponent(it) }
}

fun ComponentBuilder<*>.buttonWarning(label: String, onClick: (MouseEvent) -> Unit) {
  Component(this.newName()) {
    addClass("button")
    addClass("warning")
    text(label)
    onClick(onClick)
  }().let { this.addComponent(it) }
}