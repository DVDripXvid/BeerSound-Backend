package com.beersound.beersoundbackend.messaging

import com.beersound.beersoundbackend.messaging.event.HanSoloEvent

interface HanSoloNotifier {
    fun sendEvent(event: HanSoloEvent)
}