package com.beersound.beersoundbackend.repository

import com.beersound.beersoundbackend.entity.BeerSoundUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<BeerSoundUser, Int> {
    fun findByExternalId(externalId: String): BeerSoundUser?
}