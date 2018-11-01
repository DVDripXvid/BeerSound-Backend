package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.entity.Jamboree

interface JamboreeService {

    fun createJamboree(externalUserId: String, jamboree: NewJamboreeDto): JamboreeDto

    fun enterJamboree(externalUserId: String, code: String): JamboreeDto

    fun getJamboreesByUser(externalUserId: String): List<JamboreeDto>

    fun getJamboree(id: Int): JamboreeDto

    fun getJamboreeEntity(id: Int): Jamboree

    fun onPlaybackStopped(id: Int): JamboreeDto

    fun getUsersByJamboree(id: Int): List<BeerSoundUserDto>
}