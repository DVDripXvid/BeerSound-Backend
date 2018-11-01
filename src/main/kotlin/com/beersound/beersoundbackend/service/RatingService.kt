package com.beersound.beersoundbackend.service

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto

interface RatingService {
    fun rateTrack(externalUserId: String, jamboreeId: Int, trackId: Int, rateValue: Int): List<BeerSoundTrackDto>
}