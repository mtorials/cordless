package de.mtorials.kompore.components

interface ConditionalComponent: ReactiveComponent<Boolean> {

  fun show()
  fun hide()

  companion object {
    operator fun invoke(block: ComponentBuilder.(Boolean) -> Unit) : (Boolean) -> ConditionalComponent = { value ->
      ConditionalComponentImpl(value, block)
    }
  }
}