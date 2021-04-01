package de.mtorials.kompore.components

import kotlinx.dom.clear

fun <T> ComponentBuilder.list(list: List<T> = listOf(), block: ComponentBuilder.(T) -> Unit) : ListComponent<T> = object : ListComponent<T>() {

  override val name: String = "list"
  override val styles: MutableList<RunnableStyle> = mutableListOf()

  init {
    addAll(list)
    this@list.addComponent(this)
  }

  override fun update() {
    element.clear()
    forEach {
      val builder = ComponentBuilder()
      builder.block(it)
      element.appendChild(builder.build().element)
    }
  }
}

fun <T> ComponentBuilder.reactive(value: T, block: ComponentBuilder.(T) -> Unit) : ReactiveComponent<T> {
  return ReactiveComponent(block)(value).also { this.addComponent(it) }
}

fun ComponentBuilder.conditional(evaluationFunction: () -> Boolean, block: ComponentBuilder.() -> Unit) : ConditionalComponent {
  return ConditionalComponent(block)(evaluationFunction).also { this.addComponent(it) }
}