package de.mtorials.kompore.components

interface UpdatableComponent : Component {
  fun update()
  fun hook(component: UpdatableComponent) : UpdatableComponent
}