package de.mtorials.kompore.components

import kotlinx.dom.clear

fun <T> ComponentBuilder<MutableReactiveComponent<T>>.reactive(value: T, block: ComponentBuilder<MutableReactiveComponent<T>>.(T) -> Unit) : ReactiveComponent<T> {
  return ReactiveComponent(block)(value).also { this.addComponent(it) }
}

fun ComponentBuilder<MutableConditionalComponent>.conditional(evaluationFunction: () -> Boolean, block: ComponentBuilder<MutableConditionalComponent>.() -> Unit) : ConditionalComponent {
  val newBlock: ComponentBuilder<MutableConditionalComponent>.() -> Unit = {
    name = this@conditional.newName()
    block()
  }
  return ConditionalComponent(newBlock)(evaluationFunction).also { this.addComponent(it) }
}