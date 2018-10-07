package com.beersound.beersoundbackend.dto

import com.beersound.beersoundbackend.entity.Jamboree


data class NewJamboreeDto constructor(
        val name: String,
        val code: String
) {
    fun toEntity() = Jamboree(null, name, code, mutableListOf(), mutableListOf())
}


data class JamboreeDto(
        val id: Int,
        val name: String,
        val code: String
)