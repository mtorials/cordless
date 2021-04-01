package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import org.w3c.dom.events.MouseEvent

fun ComponentBuilder.container(block: ComponentBuilder.() -> Unit) {
  Component(newName()) {
    addClass("container")
    block()
  }().let { this.addComponent(it) }
}

fun ComponentBuilder.backgroundContainer(block: ComponentBuilder.() -> Unit) {
  Component(this.newName()) {
    addClass("background")
    addClass("container")
    block()
  }().let { this.addComponent(it) }
}