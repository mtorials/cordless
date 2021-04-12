package de.mtorials.cordless.components

import de.mtorials.kompore.components.ComponentBuilder
import de.mtorials.kompore.components.ReactiveComponent
import de.mtorials.kompore.styling.fullWidth
import de.mtorials.kompore.templates.buttonPrimary
import de.mtorials.kompore.templates.container

object RoomSelection {

  val roomTemplate = ReactiveComponent<Room> { room ->
    fullWidth()
    buttonPrimary(room.name) { println("Button pressed") }
  }

  val roomSelectorTemplate = {
    ComponentBuilder {
      name = "roomselector"
      container {
        heading("Rooms")
        addComponent(roomTemplate(Room("Test")))
      }
    }
  }
}