package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder

fun ComponentBuilder<*>.boxHorizontal(name: String = newName(), block: ComponentBuilder<*>.() -> Unit) = this.addComponent(Component(name) {
  addClass("horizontal")
  block()
}())

fun ComponentBuilder<*>.boxVertical(name: String = newName(), block: ComponentBuilder<*>.() -> Unit) = this.addComponent(Component(name) {
  addClass("vertical")
  block()
}())