package com.beersound.beersoundbackend.messaging.event

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto

data class QueueReorderedEvent(
        val queue: List<BeerSoundTrackDto>,
        override val jamboreeCode: String
) : BeerSoundEvent {
    override val type = EventType.QUEUE_REORDERED
}