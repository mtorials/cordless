package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.state.Property
import kotlinx.browser.document
import kotlinx.html.InputType
import kotlinx.html.dom.create
import kotlinx.html.js.input
import kotlinx.html.js.textArea

fun ComponentBuilder<*>.inputText(property: Property<String>, block: ComponentBuilder<*>.() -> Unit) {
  val el = document.create.input { type = InputType.text; value = property.value }
  property.updateFunctions.add { el.value = it }
  Component.new(newName(), el) {
    addClass("input")
    onInput {
      property.value = el.value
    }
    block()
  }.let { this.addComponent(it) }
}

fun ComponentBuilder<*>.textArea(property: Property<String>, autoFocus : Boolean = false, block: ComponentBuilder<*>.() -> Unit) {
  val el = document.create.textArea { text(property.value); this.autoFocus = autoFocus }
  property.updateFunctions.add { el.value = it }
  Component.new(newName(), el) {
    addClass("input")
    onInput {
      property.value = el.value
    }
    block()
  }.let { this.addComponent(it) }
}