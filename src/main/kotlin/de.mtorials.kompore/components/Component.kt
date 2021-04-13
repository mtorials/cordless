package de.mtorials.kompore.components

import de.mtorials.kompore.styling.theme.DarkTheme
import kotlinx.browser.document
import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement

interface Component {
  val element: HTMLElement
  val styles: List<RunnableStyle>
  val name: String

  companion object {
    operator fun invoke(name: String , block: ComponentBuilder<MutableComponent>.() -> Unit) : StandardComponentTemplate = { object : MutableComponent {
      override val styles: MutableList<RunnableStyle> = mutableListOf()
      override val element: HTMLElement = document.create.div {  }
      override val name: String = name

      init {
        val componentBuilder = ComponentBuilder<MutableComponent>()
        componentBuilder.block()
        componentBuilder.updateComponent(this)
      }
    } }

    fun root(title: String, theme: RunnableStyle = DarkTheme.theme, block: ComponentBuilder<MutableComponent>.() -> Unit) : Component = object : MutableComponent {

      override val element: HTMLElement = document.create.div {  }
      override val styles: MutableList<RunnableStyle> = mutableListOf()
      override val name: String = title

      init {
        document.title = title
        val componentBuilder = ComponentBuilder<MutableComponent>()
        componentBuilder.generalStyle(theme)
        componentBuilder.name = "root"
        componentBuilder.block()
        componentBuilder.updateComponent(this)
      }
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

    fun new(name: String, rootElement: HTMLElement = document.create.div {  }, block: StandardComponentBuilder.() -> Unit) : Component = object : MutableComponent {
      override val styles: MutableList<RunnableStyle> = mutableListOf()
      override val element: HTMLElement = rootElement
      override val name: String = name

      init {
        val componentBuilder = ComponentBuilder<MutableComponent>()
        componentBuilder.block()
        componentBuilder.updateComponent(this)
      }
    }
  }
}