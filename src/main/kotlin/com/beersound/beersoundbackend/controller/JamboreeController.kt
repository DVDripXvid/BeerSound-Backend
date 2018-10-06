package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.ApiResponseDto
import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.repository.JamboreeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("api/jamborees")
class JamboreeController @Autowired constructor(private val repo: JamboreeRepository) {

    @GetMapping
    fun getJamborees(): List<JamboreeDto> {
        throw NotImplementedError()
    }

    @PostMapping
    fun createJamboree(jamboree: NewJamboreeDto): JamboreeDto{
        throw NotImplementedError()
    }

    @DeleteMapping("/{jamboreeId}")
    fun disbandJamboree(jamboreeId: Int): ApiResponseDto{
        throw NotImplementedError()
    }

    @PostMapping("/enter")
    fun enterJamboree(@RequestParam code: String): ApiResponseDto{
        throw NotImplementedError()
    }

    @DeleteMapping("/{jamboreeId}/leave")
    fun leaveJamboree(jamboreeId: Int): ApiResponseDto{
        throw NotImplementedError()
    }

    @GetMapping("/{jamboreeId}/users")
    fun getJamboreeMembers(jamboreeId: Int): List<BeerSoundUserDto>{
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/resign")
    fun resignHanSolo(jamboreeId: Int): ApiResponseDto {
        throw NotImplementedError()
    }

    @PutMapping("/{jamboreeId}/apply")
    fun applyHanSolo(jamboreeId: Int): ApiResponseDto{
        throw NotImplementedError()
    }

}