package com.beersound.beersoundbackend.dto

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
import java.util.*


data class NewJamboreeDto constructor(
        val name: String
) {
    fun toEntity(hanSolo: BeerSoundUser) = Jamboree(
            null,
            name,
            false,
            UUID.randomUUID().toString(),
            hanSolo,
            null,
            null,
            mutableListOf(),
            mutableListOf(hanSolo)
    )
}


data class JamboreeDto(
        val id: Int,
        val name: String,
        val code: String,
        val isPartyTime: Boolean,
        val currentTrack: BeerSoundTrackDto?,
        val overrideCurrentTrack: BeerSoundTrackDto?,
        val hanSolo: BeerSoundUserDto
)