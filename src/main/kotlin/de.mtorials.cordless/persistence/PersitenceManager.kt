package de.mtorials.cordless.persistence

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.roomId
import kotlinx.browser.window

class PersitenceManager {

  fun setToken(token: String) {
    window.localStorage.setItem(TOKEN_COOKIE_NAME, token)
  }

  fun getToken() : String? = window.localStorage.getItem(TOKEN_COOKIE_NAME)

  fun setActiveRoom(id: RoomId) {
    window.localStorage.setItem(ACTIVE_ROOM_ID, id.toString())
  }

  fun getActiveRoom() : RoomId = window.localStorage.getItem(ACTIVE_ROOM_ID)?.roomId() ?: RoomId("")

  companion object {
    const val TOKEN_COOKIE_NAME = "cordless_token"
    const val ACTIVE_ROOM_ID = "active_room_id"
  }
}