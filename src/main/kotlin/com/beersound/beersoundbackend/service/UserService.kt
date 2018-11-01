package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.entity.BeerSoundUser

interface UserService {
    fun updateMessagingId(externalId: String, messagingId: String): BeerSoundUserDto
    fun findByExternalId(externalId: String): BeerSoundUserDto
    fun findEntityByExternalId(externalId: String): BeerSoundUser
}