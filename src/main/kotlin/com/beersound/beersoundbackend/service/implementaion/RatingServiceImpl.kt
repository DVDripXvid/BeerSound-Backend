package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import com.beersound.beersoundbackend.entity.Rating
import com.beersound.beersoundbackend.helpers.TrackRatingComparator
import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.event.QueueReorderedEvent
import com.beersound.beersoundbackend.repository.TrackRepository
import com.beersound.beersoundbackend.service.JamboreeService
import com.beersound.beersoundbackend.service.RatingService
import com.beersound.beersoundbackend.service.TrackService
import com.beersound.beersoundbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
@Transactional
class RatingServiceImpl
@Autowired
constructor(
        val trackService: TrackService,
        val trackRepository: TrackRepository,
        val jamboreeService: JamboreeService,
        val userService: UserService,
        val entityManager: EntityManager,
        val clientNotifier: ClientNotifier,
        @Value("\${rating.min}") val rateMin: Int,
        @Value("\${rating.max}") val rateMax: Int
) : RatingService {

    override fun rateTrack(externalUserId: String, jamboreeId: Int, trackId: Int, rateValue: Int): List<BeerSoundTrackDto> {
        if (rateValue !in rateMin..rateMax) {
            throw IllegalArgumentException("Rate value should be between $rateMin and $rateMax")
        }
        val user = userService.findEntityByExternalId(externalUserId)
        val jamboree = jamboreeService.getJamboreeEntity(jamboreeId)
        val tracks = jamboree.tracks
        val tracksAfterCurrent = tracks.filter { it.sequenceNumber > jamboree.currentTrack?.sequenceNumber ?: 0 }
        val track = tracksAfterCurrent.first { it.id == trackId }
        val rating = track.ratings.firstOrNull { it.user.externalId == externalUserId }
        if (rating == null) {
            track.ratings.add(Rating(id = null, track = track, user = user, value = rateValue))
        } else {
            rating.value = rateValue
        }
        val minSequenceNumber = tracksAfterCurrent.asSequence().map { it.sequenceNumber }.min() ?: 0
        val tracksSorted = tracksAfterCurrent.sortedWith(TrackRatingComparator)
        for ((index, t) in tracksSorted.withIndex()) {
            t.sequenceNumber = minSequenceNumber + index
        }
        trackRepository.saveAll(tracksSorted)
        entityManager.flush()
        val queue = trackService.getNotPlayedTracks(jamboreeId)
        val event = QueueReorderedEvent(queue, jamboree.code)
        clientNotifier.sendEvent(event)
        return trackService.getNotPlayedTracks(jamboreeId)
    }

}