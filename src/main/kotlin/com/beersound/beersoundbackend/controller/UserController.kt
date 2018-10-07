package com.beersound.beersoundbackend.controller

import com.beersound.beersoundbackend.security.userAttrName
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("api/users")
class UserController {

    @GetMapping("/me")
    fun getCurrentUser(@RequestAttribute(userAttrName) externalUserId: String): String = externalUserId

}