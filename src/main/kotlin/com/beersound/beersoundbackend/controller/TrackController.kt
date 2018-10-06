package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("api/jamborees/{jamboreeId}/tracks")
class TrackController {

    @GetMapping
    fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto> {
        throw NotImplementedError()
    }

    @PostMapping
    fun addTrackToJamboree(jamboreeId: Int, @RequestBody track: NewBeerSoundTrackDto) {
        throw NotImplementedError()
    }

    @DeleteMapping("/{trackId}")
    fun removeTrackFromJamboree(jamboreeId: Int, trackId: Int) {
        throw NotImplementedError()
    }

}