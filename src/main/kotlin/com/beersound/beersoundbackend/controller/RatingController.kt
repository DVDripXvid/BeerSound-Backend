package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.security.userAttrName
import com.beersound.beersoundbackend.service.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/jamborees/{jamboreeId}")
class RatingController @Autowired constructor(val ratingService: RatingService) {

    @PostMapping("/tracks/{trackId}/rate")
    fun rateTrack(
            @RequestAttribute(userAttrName) externalUserId: String,
            @PathVariable jamboreeId: Int,
            @PathVariable trackId: Int,
            @RequestParam("vote") vote: Int
    ) = ratingService.rateTrack(externalUserId, jamboreeId, trackId, vote)

}