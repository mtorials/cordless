package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

open class ReactiveComponentImpl<T>(override var value: T, val block: ComponentBuilder.(T) -> Unit) : ReactiveComponent<T>, MutableComponent {
  var builder: ComponentBuilder = ComponentBuilder()
  override val styles: MutableList<RunnableStyle> = mutableListOf()
  override var element: HTMLElement = document.create.div { }
  override val name: String = "asdasdasd"
  val hookedComponents: MutableList<UpdatableComponent> = mutableListOf()

  init {
    builder.block(this.value)
    builder.updateComponent(this)
  }

  override fun update(block: T.() -> Unit) {
    block(this.value)
    hookedComponents.forEach { it.update() }
    updateComponent()
  }

  protected open fun updateComponent() {
    builder = ComponentBuilder()
    builder.block(this.value)
    element.clear()
    builder.updateComponent(this)
  }

  override fun hook(component: UpdatableComponent): ReactiveComponent<T> {
    hookedComponents.add(component)
    return this
  }

  override fun set(value: T) {
    this.value = value
    hookedComponents.forEach { it.update() }
    this.updateComponent()
  }
}