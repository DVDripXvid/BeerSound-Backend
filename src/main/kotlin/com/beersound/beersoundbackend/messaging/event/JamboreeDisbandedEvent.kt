package com.beersound.beersoundbackend.messaging.event

data class JamboreeDisbandedEvent(
        val jamboreeId: Int,
        override val jamboreeCode: String
)
    : BeerSoundEvent {
    override val type = EventType.JAMBOREE_DISBANDED
}