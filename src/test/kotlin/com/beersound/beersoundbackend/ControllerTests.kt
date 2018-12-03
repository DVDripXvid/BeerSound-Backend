package com.beersound.beersoundbackend

import com.beersound.beersoundbackend.controller.JamboreeController
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.security.JwtUtil
import com.beersound.beersoundbackend.service.JamboreeService
import com.beersound.beersoundbackend.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@RunWith(SpringRunner::class)
@WebMvcTest(JamboreeController::class)
class ControllerTests {


    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var jwtUtil: JwtUtil

    @MockBean
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var jamboreeService: JamboreeService


    @Test
    @Throws(Exception::class)
    fun givenNotAuthenticated_whenGetJamborees_thenReturnError() {

        val adam = BeerSoundUser(1000, "id1", "Adam", null, null, mutableListOf(), mutableListOf(), mutableListOf())

        val jamboree = Jamboree(1, "Csoport", false, "1234", adam, null, null, arrayListOf(), arrayListOf())

        val jamborees = Arrays.asList(jamboree.toDto())

        given(jamboreeService.getJamboreesByUser(adam.externalId)).willReturn(jamborees)

        mockMvc.perform(get("api/jamborees")
                .param("userId", adam.externalId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(401))
    }

}
