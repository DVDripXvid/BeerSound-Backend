package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.event.TrackAddedEvent
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.repository.TrackRepository
import com.beersound.beersoundbackend.service.JamboreeService
import com.beersound.beersoundbackend.service.TrackService
import com.beersound.beersoundbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
@Transactional
class TrackServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val jamboreeService: JamboreeService,
        val userService: UserService,
        val trackRepository: TrackRepository,
        val clientNotifier: ClientNotifier,
        val entityManager: EntityManager
) : TrackService {

    override fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto): BeerSoundTrackDto {
        val jamboree = jamboreeService.getJamboreeEntity(jamboreeId)
        val user = userService.findEntityByExternalId(externalUserId)

        val seqNumber = (trackRepository.getMaxSequenceNumberByJamboree(jamboreeId) ?: 0) + 1
        val trackEntity = track.toEntity(seqNumber, jamboree, user)
        val createdTrack = trackRepository.save(trackEntity)
        if (jamboree.currentTrack == null) {
            jamboree.currentTrack = createdTrack
        }
        val trackDto = createdTrack.toDto()
        val event = TrackAddedEvent(trackDto, jamboree.code)
        clientNotifier.sendEvent(event)
        return trackDto
    }

    override fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto> {
        val jamboree = jamboreeService.getJamboreeEntity(jamboreeId)
        return jamboree.tracks.map { it.toDto() }
    }

    override fun onTrackStarted(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto): JamboreeDto {
        val jamboree = jamboreeService.getJamboreeEntity(jamboreeId)

        val foundTracks = trackRepository.findNotPlayedByJamboreeAndExternalId(jamboreeId, track.externalId)

        return if (foundTracks.isEmpty()) {
            val user = userService.findEntityByExternalId(externalUserId)

            val createdTrack = trackRepository.save(track.toEntity(-1, jamboree, user))
            jamboree.apply {
                overrideCurrentTrack = createdTrack
                isPartyTime = true
            }
            jamboreeRepository.save(jamboree).toDto()
        } else {
            jamboree.apply {
                currentTrack = foundTracks[0]
                overrideCurrentTrack = null
                isPartyTime = true
            }
            val updatedJamboree = jamboreeRepository.save(jamboree)
            // we need a commit here cause the @Modifying query wants to run in a separate transaction
            entityManager.flush()
            trackRepository.deleteByJamboreeAndSequenceNumber(jamboreeId, -1)
            updatedJamboree.toDto()
        }
    }

    override fun getNotPlayedTracks(jamboreeId: Int): List<BeerSoundTrackDto> {
        val notPlayedTracks = trackRepository.findNotPlayedByJamboree(jamboreeId)
        return notPlayedTracks.map { it.toDto() }
    }

}