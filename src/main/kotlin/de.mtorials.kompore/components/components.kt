package de.mtorials.kompore.components

import de.mtorials.kompore.state.Property
import kotlinx.browser.document
import kotlinx.css.CSSBuilder
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

typealias RunnableStyle = CSSBuilder.() -> Unit

object ComponentHelper {
  fun createDiv(classes: String = "") = document.create.div(classes = classes)
}

inline fun HTMLElement.insert(element: HTMLElement) : HTMLElement {
  this.appendChild(element)
  return this
}

inline fun HTMLElement.insertAll(vararg elements: HTMLElement) : HTMLElement {
  elements.forEach { elements -> this.appendChild(elements) }
  return this
}

inline fun HTMLElement.insertAll(elements: List<HTMLElement>) : HTMLElement {
  elements.forEach { elements -> this.appendChild(elements) }
  return this
}

fun UpdatableComponent.hookOn(component: UpdatableComponent) : UpdatableComponent {
  component.hook(this)
  return this
}

fun UpdatableComponent.hookOnProperty(property: Property<*>) : UpdatableComponent {
  property.hookComponent(this)
  return this
}