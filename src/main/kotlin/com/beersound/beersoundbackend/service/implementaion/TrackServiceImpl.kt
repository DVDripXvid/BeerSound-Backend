package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
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
        val trackRepository: TrackRepository
) : TrackService {

    override fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto): BeerSoundTrackDto {
        val jamboree: Jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        val user: BeerSoundUser = userRepository.findByExternalId(externalUserId)
                ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

        val seqNumber = (trackRepository.getMaxSequenceNumberByJamboree(jamboreeId) ?: 0) + 1
        val trackEntity = track.toEntity(seqNumber, jamboree, user)
        return trackRepository.save(trackEntity).toDto()
    }

    override fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto> {
        val jamboree: Jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        return jamboree.tracks.map { it.toDto() }
    }

}