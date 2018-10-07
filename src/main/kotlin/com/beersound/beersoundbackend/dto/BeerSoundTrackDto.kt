package com.beersound.beersoundbackend.dto

data class NewBeerSoundTrackDto(
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?
)

data class BeerSoundTrackDto(
        val id: Int,
        val order: Int,
        val userId: Int,
        // collected from external source
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?)