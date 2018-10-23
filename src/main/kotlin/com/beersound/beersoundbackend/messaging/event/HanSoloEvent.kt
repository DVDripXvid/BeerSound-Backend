package com.beersound.beersoundbackend.messaging.event

interface HanSoloEvent{
    val jamboreeCode: String
    val type: EventType
}

enum class EventType{
    TRACK_ADDED
}