package components

import Person
import de.mtorials.kompore.components.Component
import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.components.ComponentTemplate
import de.mtorials.kompore.components.ReactiveComponent
import kotlinx.css.*
import org.w3c.dom.events.MouseEvent

val personTemplate = ReactiveComponent { person: Person ->
  name = "person"
  heading(person.name)
  text("The age is ${person.age}")
  style {
    backgroundColor = Color.blue
  }
  onClick {
    println("The name of the person you clicked on is ${person.name}")
  }
}
