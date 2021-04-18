package de.mtorials.kompore.styling

import de.mtorials.kompore.components.ComponentBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.width

fun ComponentBuilder<*>.fullWidth() {
  this.addClass("full-width")
}

fun ComponentBuilder<*>.fullHeight() {
  this.addClass("full-height")
}

fun ComponentBuilder<*>.background() {
  this.addClass("background")
}