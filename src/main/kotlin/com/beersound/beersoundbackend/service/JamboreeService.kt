package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto

interface JamboreeService {

    fun createJamboree(externalUserId: String, jamboree: NewJamboreeDto): JamboreeDto

    fun enterJamboree(externalUserId: String, code: String): JamboreeDto?

    fun getJamboreesByUser(externalUserId: String): List<JamboreeDto>

}