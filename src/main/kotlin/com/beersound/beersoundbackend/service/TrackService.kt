package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto

interface TrackService {

    fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto)

    fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto>
}