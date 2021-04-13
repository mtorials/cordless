package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

open class ConditionalComponentImpl(
  override val evaluationFunction: () -> Boolean,
  block: ComponentBuilder<MutableConditionalComponent>.() -> Unit,
) : MutableConditionalComponent {

  var builder = ComponentBuilder<MutableConditionalComponent>()
  override val styles: MutableList<RunnableStyle> = mutableListOf()
  override val childComponents: MutableList<Component> = mutableListOf()
  override var element: HTMLElement = document.create.div { }
  override val name: String = ""
  val hookedComponents: MutableList<UpdatableComponent> = mutableListOf()

  init {
    builder.block()
    builder.updateComponent(this)
    if (!evaluationFunction()) element.clear()
  }

  override fun update() {
    hookedComponents.forEach { it.update() }
    if (evaluationFunction()) builder.updateComponent(this)
    else element.clear()
  }

  override fun hook(component: UpdatableComponent): ConditionalComponent {
    hookedComponents.add(component)
    return this
  }
}