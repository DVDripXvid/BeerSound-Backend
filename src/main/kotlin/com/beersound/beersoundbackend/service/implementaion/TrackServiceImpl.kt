package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.dto.NewBeerSoundTrackDto
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
import com.beersound.beersoundbackend.repository.JamboreeRepository
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
        val userRepository: UserRepository
) : TrackService {

    override fun addTrackToJamboree(externalUserId: String, jamboreeId: Int, track: NewBeerSoundTrackDto) {
        val jamboree: Jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        val user: BeerSoundUser = userRepository.findByExternalId(externalUserId)
                ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

        // TODO: better way of calculating sequence number?
        jamboree.tracks.add(track.toEntity(jamboree.tracks.size, jamboree, user))
        jamboreeRepository.save(jamboree)
    }

    override fun getTracksByJamboree(jamboreeId: Int): List<BeerSoundTrackDto> {
        val jamboree: Jamboree = jamboreeRepository.findById(jamboreeId).orElseThrow {
            throw EntityNotFoundException("Jamboree with id = $jamboreeId not found")
        }

        return jamboree.tracks.map { it.toDto() }
    }

}