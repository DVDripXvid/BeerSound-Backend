package com.beersound.beersoundbackend.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class NewJamboreeDto @JsonCreator constructor(
        val name: String,
        val code: String
)

data class JamboreeDto(
        val id: Int,
        val name: String
)