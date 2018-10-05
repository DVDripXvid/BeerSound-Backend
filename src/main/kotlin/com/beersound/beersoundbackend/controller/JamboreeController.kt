package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.ApiResponse
import com.beersound.beersoundbackend.dto.BeerSoundUser
import com.beersound.beersoundbackend.dto.Jamboree
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("api/jamborees")
class JamboreeController {

    @GetMapping
    fun getJamborees(): List<Jamboree> {
        throw NotImplementedError()
    }

    @PostMapping
    fun createJamboree(jamboree: Jamboree): Jamboree{
        throw NotImplementedError()
    }

    @DeleteMapping("/{jamboreeId}")
    fun disbandJamboree(jamboreeId: Int): ApiResponse{
        throw NotImplementedError()
    }

    @PostMapping("/enter")
    fun enterJamboree(@RequestParam code: String): ApiResponse{
        throw NotImplementedError()
    }

    @DeleteMapping("/{jamboreeId}/leave")
    fun leaveJamboree(jamboreeId: Int): ApiResponse{
        throw NotImplementedError()
    }

    @GetMapping("/{jamboreeId}/users")
    fun getJamboreeMembers(jamboreeId: Int): List<BeerSoundUser>{
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/resign")
    fun resignHanSolo(jamboreeId: Int): ApiResponse {
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/apply")
    fun applyHanSolo(jamboreeId: Int): ApiResponse{
        throw NotImplementedError()
    }

}