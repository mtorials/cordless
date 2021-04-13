package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.components.StandardComponentBuilder
import org.w3c.dom.events.MouseEvent

fun StandardComponentBuilder.container(block: StandardComponentBuilder.() -> Unit) {
  Component(newName()) {
    addClass("container")
    block()
  }().let { this.addComponent(it) }
}

fun StandardComponentBuilder.backgroundContainer(block: StandardComponentBuilder.() -> Unit) {
  Component(this.newName()) {
    addClass("background")
    addClass("container")
    block()
  }().let { this.addComponent(it) }
}