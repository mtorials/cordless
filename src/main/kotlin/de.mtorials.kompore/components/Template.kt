package de.mtorials.kompore.components

typealias ComponentTemplate = ComponentBuilder.() -> Component
typealias ReactiveComponentTemplate<T> = ComponentBuilder.(T) -> ReactiveComponent<T>