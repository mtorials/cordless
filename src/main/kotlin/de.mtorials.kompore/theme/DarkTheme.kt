package de.mtorials.kompore.theme

import de.mtorials.kompore.components.RunnableStyle
import kotlinx.css.*
import kotlinx.css.properties.boxShadow

object DarkTheme {

  val backgroundColor = Color("#2F2F2F")
  val middlegroundColor = Color("#424242")
  val primaryColor = Color("#FF8C00")
  val secondaryColor = Color("#172647")
  val warningColor = Color("#FF4000")
  val foreground = Color("#DDDDDD")

  val standardRadius = 10.px
  val standardGap = Gap("0.7rem")

  val theme : RunnableStyle = {

    "*" {
      color = foreground
      fontFamily = "Inconsolata, sans-serif"
    }

    body {
      padding = "0.7rem"
      margin = "0"
      backgroundColor = DarkTheme.backgroundColor
    }

    ".root" {
      display = Display.flex
      gap = standardGap
    }

    ".heading" {
      display = Display.block
      fontSize = 1.7.em
      fontWeight = FontWeight.bold
      marginBottom = 0.5.rem
    }

    ".container" {
      display = Display.flex
      flexDirection = FlexDirection.column
      padding = "1rem"
      gap = standardGap
      borderRadius = standardRadius
      backgroundColor = middlegroundColor
    }

    ".button" {
      display = Display.inline
      paddingLeft = 1.rem
      paddingRight = 1.rem
      paddingTop = 0.5.rem
      paddingBottom = 0.5.rem
      borderRadius = standardRadius
      boxShadow(Color.gray.withAlpha(0.3), 0.px, 0.px, 2.px, 2.px)
    }

    ".secondary" {
      backgroundColor = secondaryColor
    }

    ".secondary:hover" {
      backgroundColor = secondaryColor.lighten(30)
    }

    ".primary" {
      backgroundColor = primaryColor
    }

    ".primary:hover" {
      backgroundColor = primaryColor.darken(30)
    }
  }
}