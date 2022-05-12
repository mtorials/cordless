package de.mtorials.cordless

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.kompore.state.Property

class CordlessState(
  val activeRoomId: Property<RoomId>
)