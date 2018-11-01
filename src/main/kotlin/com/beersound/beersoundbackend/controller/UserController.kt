package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.security.userAttrName
import com.beersound.beersoundbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController @Autowired constructor(val userService: UserService) {

    @GetMapping("/me")
    fun getCurrentUser(@RequestAttribute(userAttrName) externalUserId: String): BeerSoundUserDto =
            userService.findByExternalId(externalUserId)

    @PutMapping("/me/messagingId")
    fun setRegistrationToken(@RequestAttribute(userAttrName) externalUserId: String, @RequestParam("messagingId") messagingId: String): BeerSoundUserDto =
            userService.updateMessagingId(externalUserId, messagingId)

}