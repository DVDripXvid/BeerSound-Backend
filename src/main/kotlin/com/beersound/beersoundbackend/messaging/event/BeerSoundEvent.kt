package com.beersound.beersoundbackend.messaging.event

interface BeerSoundEvent {
    val jamboreeCode: String
    val type: EventType
}

enum class EventType {
    TRACK_ADDED,
    PLAYBACK_STARTED,
    PLAYBACK_STOPPED,
    QUEUE_REORDERED
}