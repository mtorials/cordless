package de.mtorials.kompore.components

import de.mtorials.kompore.styling.theme.DarkTheme
import kotlinx.browser.document
import kotlinx.html.TagConsumer
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement

interface Component {
  val element: HTMLElement
  val styles: List<RunnableStyle>
  val name: String

  companion object {
    operator fun invoke(name: String , block: ComponentBuilder.() -> Unit) : ComponentTemplate = {
      val componentBuilder = ComponentBuilder()
      componentBuilder.name = name
      componentBuilder.block()
      componentBuilder.build()
    }

    fun root(title: String, theme: RunnableStyle = DarkTheme.theme, block: ComponentBuilder.() -> Unit) : Component {
      document.title = title
      val componentBuilder = ComponentBuilder()
      componentBuilder.generalStyle(theme)
      componentBuilder.name = "root"
      componentBuilder.block()
      return componentBuilder.build()
    }

    fun htmlBuilder(name: String, block: TagConsumer<HTMLElement>.() -> Unit): Component {
      val consumer = document.create
      consumer.block()
      return object : Component {
        override val name: String = name
        override val element: HTMLElement = consumer.finalize()
        override val styles: List<RunnableStyle> = listOf()
      }
    }
  }
}