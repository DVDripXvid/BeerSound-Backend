package com.beersound.beersoundbackend.dto

data class BeerSoundUserDto(
        val id: Int,
        val externalId: String,
        val displayName: String,
        val pictureUri: String?)