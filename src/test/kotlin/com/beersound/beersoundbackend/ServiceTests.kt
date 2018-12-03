package com.beersound.beersoundbackend

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.messaging.EventSubscriber
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.UserService
import com.beersound.beersoundbackend.service.implementaion.UserServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
class ServiceTests {


    @Autowired
    lateinit var userService: UserService

    @TestConfiguration
    internal class UserServiceTestContextConfiguration {

        @MockBean
        lateinit var userRepository: UserRepository

        @MockBean
        lateinit var eventSubscriber: EventSubscriber

        @Bean
        fun userService(): UserService {
            return UserServiceImpl(userRepository, eventSubscriber)
        }

    }

    @Autowired
    lateinit var userRepository: UserRepository

    @Before
    fun beforeTest() {

        val adam = BeerSoundUser(1000, "id1", "Adam", null, null, mutableListOf(), mutableListOf(), mutableListOf())
        Mockito.`when`(userRepository.findByExternalId(adam.externalId)).thenReturn(adam)
    }


    @Test
    fun whenFindByExternalId_thenReturnBeerSoundUser() {

        val externalId = "id1"

        val user = userService.findByExternalId(externalId)

        assertThat(user.externalId).isEqualTo(externalId)
    }

}
