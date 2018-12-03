package com.beersound.beersoundbackend

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@DataJpaTest
class RepositoryTests {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun whenFindByExternalId_thenReturnBeerSoundUser() {

        val adam = BeerSoundUser(1000, "id1", "Adam", null, null, mutableListOf(), mutableListOf(), mutableListOf())
        entityManager.merge(adam)
        entityManager.flush()

        val found = userRepository.findByExternalId(adam.externalId)

        assertThat(found?.externalId).isEqualTo(adam.externalId)
    }

}
