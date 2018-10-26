package com.beersound.beersoundbackend.messaging.event

interface BeerSoundEvent {
    val jamboreeCode: String
    val type: EventType
}

enum class EventType {
    TRACK_ADDED
}