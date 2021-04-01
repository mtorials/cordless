package de.mtorials.kompore.components

interface ParentComponent : Component {
  val childComponents: List<Component>
}