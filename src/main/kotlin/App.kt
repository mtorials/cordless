import components.personTemplate
import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ListComponent
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.components.reactive
import de.mtorials.kompore.templates.button
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.CSSBuilder
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.dom.create
import kotlinx.html.js.style
import kotlinx.html.unsafe

data class Person(val name: String, var age: Int)

fun main() {

  val listComponent = ListComponent<Person> {
    name = "listcomponent"
    it.run { text("The person with the name $name and age $age") }
    style {
      backgroundColor = Color.red
    }
  }

  val matti = Person("Matti", 17)

  val root = Component.root {
    component {
      name = "heading"
      heading("This is a heading!")
      onClick {
        if (it.ctrlKey) println("You clicked me with control!")
        else println("You clicked me without control!")
      }
    }
    addComponent(listComponent)
    val personComponent = personTemplate(matti)
    component {
      name = "clicker"
      text("Click me to make me older")
      onClick {
        println("Older")
        personComponent.update { age++ }
      }
    }
    component {
      name = "light"
      style {
        backgroundColor = Color.lightBlue
      }
      para("This is a parahraph")
      component { text("subcomponent") }
      component { text("another subconmpnent") }
      onClick {
        println("got clicked!")
        listComponent.add(Person("Tom", 19))
      }
    }
    button("Click me!") {
      println("Clicked!")
    }
  }

  val root2 = Component.root {
    val personComponent = reactive(matti) { person ->
      name = "person"
      heading(person.name)
      text("The person has an age of ${person.age} years.")
    }
    button("Hi") {
      println("Make me older!")
      personComponent.update { age++ }
    }
  }

  window.onload = {
    document.body?.append(root2.element)
    document.head?.append(document.create.style {
      unsafe {
        val builder = CSSBuilder()
        root2.styles.forEach { builder.apply(it) }
        raw(builder.toString())
      }
    })
  }
}