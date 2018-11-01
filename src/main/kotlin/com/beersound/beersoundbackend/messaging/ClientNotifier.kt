package com.beersound.beersoundbackend.messaging

import com.beersound.beersoundbackend.messaging.event.BeerSoundEvent

interface ClientNotifier {
    fun sendEvent(event: BeerSoundEvent)
}