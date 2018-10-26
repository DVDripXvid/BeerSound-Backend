package com.beersound.beersoundbackend.messaging.amqp

import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.event.BeerSoundEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


// @Service
class AmqpClientNotifier
@Autowired constructor(val amqpTemplate: AmqpTemplate)
    : ClientNotifier {

    override fun sendEvent(event: BeerSoundEvent) {
        amqpTemplate.convertAndSend(AmqpConfig.notifierExchangeName, event.jamboreeCode, event)
    }
}