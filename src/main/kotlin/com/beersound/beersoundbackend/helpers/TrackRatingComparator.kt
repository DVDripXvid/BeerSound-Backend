package com.beersound.beersoundbackend.helpers

import com.beersound.beersoundbackend.entity.BeerSoundTrack

class TrackRatingComparator {

    companion object : Comparator<BeerSoundTrack> {
        override fun compare(track1: BeerSoundTrack, track2: BeerSoundTrack): Int {
            val score1 = track1.ratings.asSequence().map { it.value }.sum()
            val score2 = track2.ratings.asSequence().map { it.value }.sum()
            return score2 - score1
        }

    }

}