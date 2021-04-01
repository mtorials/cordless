package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

abstract class ListComponent<T>(
  classes: String? = null
) : Component, ArrayList<T>() {

  override val element: HTMLElement = document.create.div(classes)

  override fun add(element: T): Boolean {
    val result = super.add(element)
    update()
    return result
  }

  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    update()
    return result
  }

  override fun clear() {
    super.clear()
    update()
  }

  override fun addAll(elements: Collection<T>): Boolean {
    val result = super.addAll(elements)
    update()
    return result
  }

  abstract fun update()

  companion object {
    operator fun <T> invoke(block: ComponentBuilder.(T) -> Unit) : ListComponent<T> = object : ListComponent<T>() {

      override val name: String = "list"
      override val styles: MutableList<RunnableStyle> = mutableListOf()

      override fun update() {
        element.clear()
        forEach {
          val builder = ComponentBuilder()
          builder.block(it)
          element.appendChild(builder.build().element)
        }
      }
    }
  }
}