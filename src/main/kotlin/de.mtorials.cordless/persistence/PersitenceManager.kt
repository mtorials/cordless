package de.mtorials.cordless.persistence

import kotlinx.browser.window

class PersitenceManager {

  fun setToken(token: String) {
    window.localStorage.setItem(TOKEN_COOKIE_NAME, token)
  }

  fun getToken() : String? = window.localStorage.getItem(TOKEN_COOKIE_NAME)

  fun setActiveRoom(id: String) {
    window.localStorage.setItem(ACTIVE_ROOM_ID, id)
  }

  fun getActiveRoom() : String = window.localStorage.getItem(ACTIVE_ROOM_ID) ?: ""

  companion object {
    const val TOKEN_COOKIE_NAME = "cordless_token"
    const val ACTIVE_ROOM_ID = "active_room_id"
  }
}