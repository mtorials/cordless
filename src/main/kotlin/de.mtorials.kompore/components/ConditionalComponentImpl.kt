package de.mtorials.kompore.components

import kotlinx.dom.clear

open class ConditionalComponentImpl(
  override var value: Boolean,
  block: ComponentBuilder.(Boolean) -> Unit,
) : ReactiveComponentImpl<Boolean>(value, block), ConditionalComponent {
  override fun show() {
    this.value = true
    updateComponent()
  }

  override fun hide() {
    this.value = false
    updateComponent()
  }

  override fun update(block: (Boolean) -> Unit) {
    block(value)
    updateComponent()
  }

  override fun updateComponent() {
    if (value) super.updateComponent()
    else this.element.clear()
  }
}