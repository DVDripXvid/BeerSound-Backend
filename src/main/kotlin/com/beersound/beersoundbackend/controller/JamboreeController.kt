package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.ApiResponse
import com.beersound.beersoundbackend.dto.BeerSoundUser
import com.beersound.beersoundbackend.dto.Jamboree
import com.beersound.beersoundbackend.repository.JamboreeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("api/jamborees")
class JamboreeController @Autowired constructor(private val repo: JamboreeRepository) {
    
    @GetMapping
    fun getJamborees(): List<Jamboree> {
        return repo.findAll().toList()
    }

    @PostMapping
    fun createJamboree(jamboree: Jamboree): Jamboree{
        return repo.save(jamboree)
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