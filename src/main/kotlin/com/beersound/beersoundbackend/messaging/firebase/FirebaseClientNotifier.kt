package com.beersound.beersoundbackend.messaging.firebase

import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.event.BeerSoundEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FirebaseClientNotifier
@Autowired
constructor(val objectMapper: ObjectMapper, val firebaseMessaging: FirebaseMessaging)
    : ClientNotifier {

    override fun sendEvent(event: BeerSoundEvent) {
        val json = objectMapper.writeValueAsString(event)
        val message = Message.builder()
                .putData(TYPE_NAME, event.type.name)
                .putData(EVENT_NAME, json)
                .setTopic(event.jamboreeCode)
                .build()
        firebaseMessaging.send(message)
    }

    companion object {
        private const val TYPE_NAME = "type"
        private const val EVENT_NAME = "event"
    }
}