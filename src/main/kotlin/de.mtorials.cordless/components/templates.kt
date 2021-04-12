package de.mtorials.cordless.components

import Person
import de.mtorials.kompore.components.ReactiveComponent
import kotlinx.css.*

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
