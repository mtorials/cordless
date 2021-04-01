package de.mtorials.kompore.components

interface MutableComponent : Component {
  override val styles: MutableList<RunnableStyle>
}