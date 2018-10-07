package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.security.userAttrName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController()
@RequestMapping("api/users")
class UserController @Autowired constructor(val userRepository: UserRepository) {

    @GetMapping("/me")
    fun getCurrentUser(@RequestAttribute(userAttrName) externalUserId: String): BeerSoundUserDto =
            userRepository.findByExternalId(externalUserId)?.toDto()
                    ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

}