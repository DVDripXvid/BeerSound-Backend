package com.beersound.beersoundbackend.messaging.event

import com.beersound.beersoundbackend.dto.JamboreeDto

data class PlaybackStoppedEvent(
        val jamboree: JamboreeDto,
        override val jamboreeCode: String
) : BeerSoundEvent {
    override val type = EventType.PLAYBACK_STOPPED
}