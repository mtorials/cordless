package de.mtorials.kompore.components

interface ConditionalComponent: ParentComponent, UpdatableComponent {

  val evaluationFunction: () -> Boolean

  companion object {
    operator fun invoke(block: ComponentBuilder<MutableConditionalComponent>.() -> Unit) : (() -> Boolean) -> ConditionalComponent = { function ->
      ConditionalComponentImpl(function, block)
    }
  }
}