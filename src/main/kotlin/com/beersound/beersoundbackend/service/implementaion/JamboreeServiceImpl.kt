package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.entity.Jamboree
import com.beersound.beersoundbackend.messaging.ClientNotifier
import com.beersound.beersoundbackend.messaging.EventSubscriber
import com.beersound.beersoundbackend.messaging.event.JamboreeDisbandedEvent
import com.beersound.beersoundbackend.messaging.event.PlaybackStoppedEvent
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.JamboreeService
import com.beersound.beersoundbackend.service.UserService
import com.wrapper.spotify.exceptions.detailed.UnauthorizedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class JamboreeServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val userService: UserService,
        val userRepository: UserRepository,
        val eventSubscriber: EventSubscriber,
        val clientNotifier: ClientNotifier
) : JamboreeService {

    override fun getJamboree(id: Int): JamboreeDto =
            getJamboreeEntity(id).toDto()

    override fun getJamboreeEntity(id: Int): Jamboree =
            jamboreeRepository.findById(id).orElseThrow {
                throw EntityNotFoundException("Jamboree with id = $id not found")
            }

    override fun createJamboree(externalUserId: String, jamboree: NewJamboreeDto): JamboreeDto {
        val user = userService.findEntityByExternalId(externalUserId)
        val createdJamboree = jamboreeRepository.save(jamboree.toEntity(user))
        eventSubscriber.subscribeUserToJamboreeEvents(user.toDto(), createdJamboree.code)
        return createdJamboree.toDto()
    }

    override fun disbandJamboree(externalUserId: String, jamboreeId: Int) {
        val user = userService.findEntityByExternalId(externalUserId)
        val jamboree = getJamboreeEntity(jamboreeId)

        if(user.controlledJamborees.removeIf{ j -> j.id == jamboreeId }){
            userRepository.save(user)
            //TODO: unsubscribe
            jamboreeRepository.deleteById(jamboreeId)
            val event = JamboreeDisbandedEvent(jamboreeId, jamboree.code)
            clientNotifier.sendEvent(event)
        } else {
            throw UnauthorizedException("You are not the Han Solo")
        }
    }

    override fun enterJamboree(externalUserId: String, code: String): JamboreeDto {
        val user = userService.findEntityByExternalId(externalUserId)

        val jamboree = jamboreeRepository.findByCode(code)
                ?: throw EntityNotFoundException("Jamboree with code = $code not found")

        if (!jamboree.users.contains(user)) {
            jamboree.users += user
            eventSubscriber.subscribeUserToJamboreeEvents(user.toDto(), jamboree.code)
        }
        jamboreeRepository.save(jamboree)
        return jamboree.toDto()
    }

    override fun leaveJamboree(externalUserId: String, jamboreeId: Int) {
        val user = userService.findEntityByExternalId(externalUserId)
        val jamboree = getJamboreeEntity(jamboreeId)

        jamboree.users.removeIf { u -> u.externalId == externalUserId }
        eventSubscriber.unsubscribeUserFromJamboreeEvents(user.toDto(), jamboree.code)

        jamboreeRepository.save(jamboree)
    }

    override fun getJamboreesByUser(externalUserId: String): List<JamboreeDto> {
        val jamborees = userService.findEntityByExternalId(externalUserId).jamborees
        return jamborees.map { it.toDto() }
    }

    override fun onPlaybackStopped(id: Int): JamboreeDto {
        val jamboree = getJamboreeEntity(id)
        jamboree.isPartyTime = false

        val event = PlaybackStoppedEvent(jamboree.toDto(), jamboree.code)
        clientNotifier.sendEvent(event)

        return jamboreeRepository.save(jamboree).toDto()
    }

    override fun getUsersByJamboree(id: Int): List<BeerSoundUserDto> {
        val jamboree = getJamboreeEntity(id)
        return jamboree.users.map { it.toDto() }
    }
}