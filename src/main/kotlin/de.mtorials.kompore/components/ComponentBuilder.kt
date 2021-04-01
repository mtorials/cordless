package de.mtorials.kompore.components

import kotlinx.browser.document
import kotlinx.css.CSSBuilder
import kotlinx.dom.clear
import kotlinx.html.dom.create
import kotlinx.html.js.a
import kotlinx.html.js.div
import kotlinx.html.js.h1
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent

class ComponentBuilder {

  var name: String = ""
  private val childComponents: MutableList<Component> = mutableListOf()
  private var onClick: (MouseEvent) -> Unit = {}
  private var onMousOver: (MouseEvent) -> Unit = {}
  private var style: CSSBuilder.() -> Unit = {}
  private var hoverStyle: CSSBuilder.() -> Unit = {}

  fun component(name: String = newName(), block: ComponentBuilder.() -> Unit) {
    val builder = ComponentBuilder()
    builder.name = name
    builder.block()
    childComponents.add(builder.build())
  }

  fun text(string: String) = childComponents.add(Component.htmlBuilder(newName()) { a{ +string } })
  fun para(string: String) = childComponents.add(Component.htmlBuilder(newName()) { p { +string } })
  fun heading(string: String) = childComponents.add(Component.htmlBuilder(newName()) { h1 { +string } })

  fun addComponent(component: Component) = childComponents.add(component)

  fun style(block: CSSBuilder.() -> Unit) {
    style = {
      ".$name" {
        block()
      }
    }
  }

  fun styleHover(block: RunnableStyle) {
    hoverStyle = {
      ".$name:hover" {
        block()
      }
    }
  }

  fun onClick(block: (MouseEvent) -> Unit) { onClick = block }
  fun onMouseOver(block: (MouseEvent) -> Unit) { onMousOver = block }

  fun build() : Component = object : Component {
    override val element: HTMLElement = document.create.div(this@ComponentBuilder.name)
    override val name: String = this@ComponentBuilder.name
    override val styles: MutableList<RunnableStyle> = mutableListOf(this@ComponentBuilder.style)
    init {
      styles.add(hoverStyle)
      childComponents.forEach { element.insert(it.element) }
      childComponents.forEach { styles.addAll(it.styles) }
      element.onclick = onClick
      element.onmouseover = onMousOver
    }
  }

  fun updateComponent(component: MutableComponent) {
    component.element.setAttribute("class", name)
    component.element.clear()
    component.styles.clear()
    component.styles.add(style)
    component.styles.add(hoverStyle)
    childComponents.forEach {
      component.element.insert(it.element)
      component.styles.addAll(it.styles)
    }
    component.element.onclick = onClick
    component.element.onmouseover = onMousOver
  }

  private fun newName() : String = this.name + childComponents.size
}