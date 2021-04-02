import components.RoomSelection
import components.personTemplate
import de.mtorials.kompore.components.*
import de.mtorials.kompore.styling.fullWidth
import de.mtorials.kompore.styling.isSecondary
import de.mtorials.kompore.templates.button
import de.mtorials.kompore.templates.buttonPrimary
import de.mtorials.kompore.templates.container
import de.mtorials.kompore.theme.DarkTheme
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.CSSBuilder
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.color
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

  val personComponent = ReactiveComponent<Person> { person ->
    name = "person"
    heading(person.name)
    text("The person has an age of ${person.age} years.")
    if (person.age >= 18) text("Can drive in EU")
  }(matti)

  val root2 = Component.root("cordless") {
    addComponent(RoomSelection.roomSelectorTemplate())
    container {
      fullWidth()
      addComponent(personComponent)

      conditional({ personComponent.value.age > 20 }) {
        text("Over 20 years!")
      }.hookOn(personComponent)

      container {
        isSecondary()
        heading("This is another container")
      }

      button("Hi") {
        println("Make me older!")
        personComponent.update { age++ }
      }
    }
    container {
      heading("This is another container")
      buttonPrimary("Hendrik") {
        personComponent.set(Person("Hendrik", 25))
      }
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