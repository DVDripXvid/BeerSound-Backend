package com.beersound.beersoundbackend.messaging.amqp

import com.beersound.beersoundbackend.messaging.HanSoloNotifier
import com.beersound.beersoundbackend.messaging.event.HanSoloEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AmqpHanSoloNotifier
@Autowired constructor(val amqpTemplate: AmqpTemplate)
    : HanSoloNotifier {

    override fun sendEvent(event: HanSoloEvent) {
        amqpTemplate.convertAndSend(AmqpConfig.notifierExchangeName, event.jamboreeCode, event)
    }
}