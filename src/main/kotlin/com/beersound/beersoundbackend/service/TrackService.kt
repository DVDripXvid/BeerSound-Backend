package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto

interface TrackService {

    fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto): BeerSoundTrackDto

    fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto>

    fun onTrackStarted(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto)

    fun getNotPlayedTracks(jamboreeId: Int): List<BeerSoundTrackDto>
}