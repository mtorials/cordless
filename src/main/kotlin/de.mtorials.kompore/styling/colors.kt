package de.mtorials.kompore.styling

import de.mtorials.kompore.components.ComponentBuilder

fun ComponentBuilder<*>.isPrimary() = this.addClass("primary")
fun ComponentBuilder<*>.isSecondary() = this.addClass("secondary")
fun ComponentBuilder<*>.isWarning() = this.addClass("warning")