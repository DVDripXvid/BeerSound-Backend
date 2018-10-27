package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.ApiResponseDto
import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.security.userAttrName
import com.beersound.beersoundbackend.service.JamboreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/jamborees")
class JamboreeController @Autowired constructor(private val jamboreeService: JamboreeService) {

    @GetMapping
    fun getJamborees(@RequestAttribute(userAttrName) externalUserId: String): List<JamboreeDto> =
            jamboreeService.getJamboreesByUser(externalUserId)

    @PostMapping
    fun createJamboree(@RequestAttribute(userAttrName) externalUserId: String, @RequestBody jamboree: NewJamboreeDto) =
            jamboreeService.createJamboree(externalUserId, jamboree)

    @GetMapping("/{jamboreeId}")
    fun getJamboree(@PathVariable("jamboreeId") jamboreeId: Int): JamboreeDto = jamboreeService.getJamboree(jamboreeId)

    @DeleteMapping("/{jamboreeId}")
    fun disbandJamboree(jamboreeId: Int): ApiResponseDto {
        throw NotImplementedError()
    }

    @PostMapping("/enter")
    fun enterJamboree(@RequestAttribute(userAttrName) externalUserId: String, @RequestParam code: String): JamboreeDto =
            jamboreeService.enterJamboree(externalUserId, code)

    @DeleteMapping("/{jamboreeId}/leave")
    fun leaveJamboree(jamboreeId: Int): ApiResponseDto {
        throw NotImplementedError()
    }

    @GetMapping("/{jamboreeId}/stop")
    fun playbackStopped(jamboreeId: Int) = jamboreeService.onPlaybackStopped(jamboreeId)

    @GetMapping("/{jamboreeId}/users")
    fun getJamboreeMembers(jamboreeId: Int): List<BeerSoundUserDto> {
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/resign")
    fun resignHanSolo(jamboreeId: Int): ApiResponseDto {
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/apply")
    fun applyHanSolo(jamboreeId: Int): ApiResponseDto {
        throw NotImplementedError()
    }

}