package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.event.TrackAddedEvent
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.repository.TrackRepository
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.TrackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class TrackServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val userRepository: UserRepository,
        val trackRepository: TrackRepository,
        val clientNotifier: ClientNotifier
) : TrackService {

    override fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto): BeerSoundTrackDto {
        val jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        val user = userRepository.findByExternalId(externalUserId)
                ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

        val seqNumber = (trackRepository.getMaxSequenceNumberByJamboree(jamboreeId) ?: 0) + 1
        val trackEntity = track.toEntity(seqNumber, jamboree, user)
        val createdTrack = trackRepository.save(trackEntity)
        if(jamboree.currentTrack == null){
            jamboree.currentTrack = createdTrack
        }
        val trackDto = createdTrack.toDto()
        val event = TrackAddedEvent(trackDto, jamboree.code)
        clientNotifier.sendEvent(event)
        return trackDto
    }

    override fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto> {
        val jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        return jamboree.tracks.map { it.toDto() }
    }

    override fun onTrackStarted(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto) {
        val jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        val foundTracks = trackRepository.findNotPlayedByExternalId(jamboreeId, track.externalId)

        if (foundTracks.isEmpty()) {
            val user = userRepository.findByExternalId(externalUserId)
                    ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

            val createdTrack = trackRepository.save(track.toEntity(-1, jamboree, user))
            jamboree.apply {
                overrideCurrentTrack = createdTrack
                isPartyTime = true
            }
        } else {
            trackRepository.deleteByJamboreeAndSequenceNumber(jamboreeId, -1)
            jamboree.apply{
                currentTrack = foundTracks[0]
                overrideCurrentTrack = null
                isPartyTime = true
            }
        }
        jamboreeRepository.save(jamboree)
    }

}