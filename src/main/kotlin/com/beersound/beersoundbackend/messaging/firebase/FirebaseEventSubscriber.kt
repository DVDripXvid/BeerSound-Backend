package com.beersound.beersoundbackend.messaging.firebase

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.messaging.EventSubscriber
import com.google.firebase.messaging.FirebaseMessaging
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FirebaseEventSubscriber
@Autowired
constructor(val firebaseMessaging: FirebaseMessaging)
    : EventSubscriber {

    private val logger = LoggerFactory.getLogger(FirebaseEventSubscriber::class.java)!!

    override fun subscribeUserToJamboreeEvents(user: BeerSoundUserDto, jamboreeCode: String) {
        if (user.messagingId.isNullOrBlank()) {
            logger.error("user with id = ${user.id} has no messaging id")
        }
        val response = firebaseMessaging.subscribeToTopic(mutableListOf(user.messagingId), jamboreeCode)
        if (response.failureCount > 0) {
            logger.error("Cannot subscribe user (${user.id}) to jamboree events (code = $jamboreeCode). Due to the following reason:")
            response.errors.forEach {
                logger.error(it.reason)
            }
        }
    }

    override fun unsubscribeUserFromJamboreeEvents(user: BeerSoundUserDto, jamboreeCode: String) {
        if (user.messagingId.isNullOrBlank()) {
            logger.error("user with id = ${user.id} has no messaging id")
        }
        val response = firebaseMessaging.unsubscribeFromTopic(mutableListOf(user.messagingId), jamboreeCode)
        if (response.failureCount > 0) {
            logger.error("Cannot unsubscribe user (${user.id}) from jamboree events (code = $jamboreeCode). Due to the following reason:")
            response.errors.forEach {
                logger.error(it.reason)
            }
        }
    }

}