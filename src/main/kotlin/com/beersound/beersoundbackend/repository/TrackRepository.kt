package com.beersound.beersoundbackend.repository

import com.beersound.beersoundbackend.entity.BeerSoundTrack
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : CrudRepository<BeerSoundTrack, Int> {

    @Query("SELECT MAX(t.sequenceNumber) FROM Jamboree j JOIN j.tracks t WHERE j.id = ?1")
    fun getMaxSequenceNumberByJamboree(jamboreeId: Int): Int?

    @Query("SELECT t FROM Jamboree j JOIN j.tracks t WHERE j.id = ?1 AND t.externalId = ?2 AND (j.currentTrack is null OR t.sequenceNumber > j.currentTrack.sequenceNumber) ORDER BY t.sequenceNumber")
    fun findNotPlayedByJamboreeAndExternalId(jamboreeId: Int, externalId: String): List<BeerSoundTrack>

    @Modifying
    @Query("DELETE FROM BeerSoundTrack t WHERE t.jamboree.id = ?1 AND t.sequenceNumber = ?2")
    fun deleteByJamboreeAndSequenceNumber(jamboreeId: Int, sequenceNumber: Int)

    @Query("SELECT t FROM Jamboree j JOIN j.tracks t WHERE j.id = ?1 AND (j.currentTrack is null OR t.sequenceNumber > j.currentTrack.sequenceNumber) ORDER BY t.sequenceNumber")
    fun findNotPlayedByJamboree(jamboreeId: Int): List<BeerSoundTrack>
}