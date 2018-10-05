package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.BeerSoundTrack
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("api/jamborees/{jamboreeId}/tracks")
class TrackController {

    @GetMapping
    fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrack> {
        throw NotImplementedError()
    }

    @PostMapping
    fun addTrackToJamboree(jamboreeId: Int, @RequestBody track: BeerSoundTrack) {
        throw NotImplementedError()
    }

    @DeleteMapping("/{trackId}")
    fun removeTrackFromJamboree(jamboreeId: Int, trackId: Int) {
        throw NotImplementedError()
    }

}