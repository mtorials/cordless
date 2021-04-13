package de.mtorials.kompore.styling

import de.mtorials.kompore.components.ComponentBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.width

fun ComponentBuilder<*>.fullWidth() {
  style {
    width = LinearDimension("100%")
  }
}