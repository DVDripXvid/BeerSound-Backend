package com.beersound.beersoundbackend.dto

import com.beersound.beersoundbackend.entity.BeerSoundTrack
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree

data class NewBeerSoundTrackDto(
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?
) {
    fun toEntity(sequenceNumber: Int, jamboree: Jamboree, user: BeerSoundUser) = BeerSoundTrack(null, sequenceNumber, externalId, title, artist, album, durationInMs, albumImageUrl, jamboree, user, mutableListOf())
}

data class BeerSoundTrackDto(
        val id: Int,
        val sequenceNumber: Int,
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?,
        val numberOfVotes: Int,
        val averageRating: Double
)
