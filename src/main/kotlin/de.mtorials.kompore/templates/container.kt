package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.components.StandardComponentBuilder
import de.mtorials.kompore.styling.isSecondary
import org.w3c.dom.events.MouseEvent

fun ComponentBuilder<*>.container(block: ComponentBuilder<*>.() -> Unit) {
  Component(newName()) {
    addClass("container")
    isSecondary()
    block()
  }().let { this.addComponent(it) }
}

fun ComponentBuilder<*>.containerBackground(block: ComponentBuilder<*>.() -> Unit) {
  Component(this.newName()) {
    addClass("background")
    addClass("container")
    block()
  }().let { this.addComponent(it) }
}