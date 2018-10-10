package com.beersound.beersoundbackend.dto

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
import java.util.*


data class NewJamboreeDto constructor(
        val name: String
) {
    fun toEntity(hanSolo: BeerSoundUser) = Jamboree(null, name, UUID.randomUUID().toString(), hanSolo, mutableListOf(), mutableListOf(hanSolo))
}


data class JamboreeDto(
        val id: Int,
        val name: String,
        val code: String,
        val hanSolo: BeerSoundUserDto
)