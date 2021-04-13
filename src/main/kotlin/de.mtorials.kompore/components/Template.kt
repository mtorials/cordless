package de.mtorials.kompore.components

typealias StandardComponentTemplate = () -> Component
typealias ReactiveComponentTemplate<T> = (T) -> ReactiveComponent<T>