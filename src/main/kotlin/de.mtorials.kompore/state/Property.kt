package de.mtorials.kompore.state

import de.mtorials.kompore.components.UpdatableComponent

class Property<T>(
  initial: T,
  onUpdate: (T) -> Unit = {}
) {

  val updateFunctions: MutableList<(T) -> Unit> = mutableListOf(onUpdate)
  val hookedComponents: MutableList<UpdatableComponent> = mutableListOf()

  var value: T = initial
    set(value) {
      field = value
      update()
    }

  fun update() {
    updateFunctions.forEach { it(value) }
    hookedComponents.forEach { it.update() }
  }

  fun hookComponent(component: UpdatableComponent) = hookedComponents.add(component)
}