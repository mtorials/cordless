package de.mtorials.kompore.components

interface ConditionalComponent: ParentComponent, UpdatableComponent {

  val evaluationFunction: () -> Boolean

  companion object {
    operator fun invoke(block: ComponentBuilder.() -> Unit) : (() -> Boolean) -> ConditionalComponent = { function ->
      ConditionalComponentImpl(function, block)
    }
  }
}