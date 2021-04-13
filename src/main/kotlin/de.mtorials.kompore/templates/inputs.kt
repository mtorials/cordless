package de.mtorials.kompore.templates

import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.state.Property
import kotlinx.browser.document
import kotlinx.html.InputType
import kotlinx.html.dom.create
import kotlinx.html.js.input

fun ComponentBuilder<*>.inputText(property: Property<String>) {
  val el = document.create.input { type = InputType.text; value = property.value }
  property.updateFunctions.add { el.value = it }
  Component.new(newName(), el) {
    onInput {
      property.value = el.value
    }
  }.let { this.addComponent(it) }
}