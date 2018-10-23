package com.beersound.beersoundbackend.messaging.amqp

import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AmqpConfig {

    @Bean
    fun getHanSoloNotifierExchange() = TopicExchange(notifierExchangeName)

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    companion object {
        const val notifierExchangeName = "amqp.beersound.notifications"
    }

}