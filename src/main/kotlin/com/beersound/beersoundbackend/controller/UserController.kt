package com.beersound.beersoundbackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("api/users")
class UserController {

    @GetMapping("/me")
    fun getCurrentUser(@RequestAttribute userId: String): String = userId

}