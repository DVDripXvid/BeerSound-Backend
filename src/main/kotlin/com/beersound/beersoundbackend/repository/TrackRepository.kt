package com.beersound.beersoundbackend.repository

import com.beersound.beersoundbackend.entity.BeerSoundTrack
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository: CrudRepository<BeerSoundTrack, Int> {

    @Query("SELECT MAX(t.sequenceNumber) From Jamboree j JOIN j.tracks t WHERE j.id = ?1")
    fun getMaxSequenceNumberByJamboree(jamboreeId: Int): Int?

}