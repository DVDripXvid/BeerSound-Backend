package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import com.beersound.beersoundbackend.security.userAttrName
import com.beersound.beersoundbackend.service.TrackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/jamborees/{jamboreeId}/tracks")
class TrackController @Autowired constructor(private val trackService: TrackService) {

    @GetMapping
    fun getTracksByJamboree(@PathVariable jamboreeId: Int): List<BeerSoundTrackDto> =
            trackService.getTracksByJamboree(jamboreeId)

    @PostMapping
    fun addTrackToJamboree(
            @RequestAttribute(userAttrName) externalUserId: String,
            @PathVariable jamboreeId: Int,
            @RequestBody track: NewBeerSoundTrackDto
    ) = trackService.addTrackToJamboree(externalUserId, jamboreeId, track)

    @DeleteMapping("/{trackId}")
    fun removeTrackFromJamboree(jamboreeId: Int, trackId: Int) {
        throw NotImplementedError()
    }

}