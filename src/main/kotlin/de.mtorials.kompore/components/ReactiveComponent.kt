package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement

/**
 * Styles are not reactive
 * handlers?
 */
interface ReactiveComponent<T> : Component, UpdatableComponent {
  val value: T
  fun update(block:T.() -> Unit = {})
  override fun update() = this.update {}

  companion object {
    operator fun <T> invoke(block: ComponentBuilder.(T) -> Unit) : (T) -> ReactiveComponent<T> = { value ->
      ReactiveComponentImpl(value, block)
    }
  }
}