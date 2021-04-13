package de.mtorials.kompore.components

typealias StandardComponentBuilder = ComponentBuilder<MutableComponent>
typealias ReactiveComponentBuilder<T> = ComponentBuilder<MutableReactiveComponent<T>>
typealias ConditionalComponentBuilder = ComponentBuilder<MutableConditionalComponent>