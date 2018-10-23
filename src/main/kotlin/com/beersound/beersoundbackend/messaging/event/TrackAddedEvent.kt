package com.beersound.beersoundbackend.messaging.event

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto

data class TrackAddedEvent(
        val track: BeerSoundTrackDto,
        override val jamboreeCode: String
) : HanSoloEvent {
    override val type = EventType.TRACK_ADDED
}