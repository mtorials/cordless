package de.mtorials.kompore.routing

import kotlinx.browser.window

class Router() {

  fun onLoad() {
    println(window.location.href)
  }
}