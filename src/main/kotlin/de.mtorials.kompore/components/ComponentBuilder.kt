package de.mtorials.kompore.components

import io.ktor.util.date.*
import kotlinx.browser.document
import kotlinx.css.CSSBuilder
import kotlinx.dom.addClass
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.a
import kotlinx.html.js.div
import kotlinx.html.js.h1
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.InputEvent
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import kotlin.random.Random

open class ComponentBuilder<T : MutableComponent> {

  var name: String = ""
  private val classes: MutableList<String> = mutableListOf()
  private val childComponents: MutableList<Component> = mutableListOf()
  private var styles: MutableList<RunnableStyle> = mutableListOf()

  private var onClick: (MouseEvent) -> Unit = {}
  private var onMousOver: (MouseEvent) -> Unit = {}
  private var onInput: (InputEvent) -> Unit = {}
  private var onKeyPress: (KeyboardEvent) -> Unit = {}

  fun component(name: String = newName(), block: ComponentBuilder<MutableComponent>.() -> Unit) {
    val builder = ComponentBuilder<MutableComponent>()
    builder.name = name
    builder.block()
    childComponents.add(Component(newName()) { block() }())
  }

  fun text(string: String) = childComponents.add(Component.htmlBuilder(newName()) { a{ +string } })
  fun para(string: String) = childComponents.add(Component.htmlBuilder(newName()) { p { +string } })
  fun heading(string: String) = childComponents.add(Component.htmlBuilder(newName()) { a(classes = "heading") { +string } })

  fun addComponent(component: Component) = childComponents.add(component)

  fun style(block: RunnableStyle) {
    styles.add {
      ".$name" {
        block()
      }
    }
  }

  fun generalStyle(block: RunnableStyle) {
    styles.add(block)
  }

  fun styleHover(block: RunnableStyle) {
    styles.add {
      ".$name:hover" {
        block()
      }
    }
  }

  fun addClass(clazz: String) { this.classes.add(clazz) }

  fun onClick(block: (MouseEvent) -> Unit) { onClick = block }
  fun onMouseOver(block: (MouseEvent) -> Unit) { onMousOver = block }
  fun onInput(block: (InputEvent) -> Unit) { onInput = block }
  fun onKeyPress(block: (KeyboardEvent) -> Unit) { onKeyPress = block }

  /**
   * overrides onKeyPress
   */
  fun onEnter(block: (KeyboardEvent) -> Unit) {
    onKeyPress = { event ->
      if (event.keyCode == 13 && !event.altKey && !event.ctrlKey && !event.shiftKey ) block(event)
    }
  }

  fun updateComponent(component: T) {
    component.element.clear()
    component.element.id = Random(getTimeMillis()).nextInt().toString()
    component.styles.clear()
    component.styles.addAll(styles)
    component.element.addClass(name)
    classes.forEach { component.element.addClass(it) }
    childComponents.forEach {
      component.element.insert(it.element)
      component.styles.addAll(it.styles)
    }
    component.element.onclick = onClick
    component.element.onmouseover = onMousOver
    component.element.oninput = onInput
    component.element.onkeypress = onKeyPress
  }

  fun newName() : String = this.name + childComponents.size
}