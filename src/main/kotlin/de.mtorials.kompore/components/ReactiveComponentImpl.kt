package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

open class ReactiveComponentImpl<T>(override val value: T, val block: ComponentBuilder.(T) -> Unit) : ReactiveComponent<T>, MutableComponent {
  var builder: ComponentBuilder = ComponentBuilder()
  override val styles: MutableList<RunnableStyle> = mutableListOf()
  override var element: HTMLElement = document.create.div { }
  override val name: String = "asdasdasd"

  init {
    builder.block(this.value)
    builder.updateComponent(this)
  }

  override fun update(block: T.() -> Unit) {
    block(this.value)
    updateComponent()
  }

  protected open fun updateComponent() {
    builder = ComponentBuilder()
    builder.block(this.value)
    element.clear()
    builder.updateComponent(this)
  }
}