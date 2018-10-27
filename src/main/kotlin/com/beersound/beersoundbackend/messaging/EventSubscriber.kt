package com.beersound.beersoundbackend.messaging

import com.beersound.beersoundbackend.dto.BeerSoundUserDto

interface EventSubscriber {
    fun subscribeUserToJamboreeEvents(user: BeerSoundUserDto, jamboreeCode: String)
    fun unsubscribeUserFromJamboreeEvents(user: BeerSoundUserDto, jamboreeCode: String)
}