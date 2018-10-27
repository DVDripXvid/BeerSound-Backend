package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.messaging.EventSubscriber
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class UserServiceImpl
@Autowired
constructor(val userRepository: UserRepository, val eventSubscriber: EventSubscriber)
    : UserService {
    override fun updateMessagingId(externalId: String, messagingId: String): BeerSoundUserDto {
        val user = findEntityByExternalId(externalId)
        val oldMessagingId = user.messagingId
        if (oldMessagingId == messagingId) {
            return user.toDto()
        }
        val jamborees = user.jamborees
        jamborees.forEach {
            eventSubscriber.unsubscribeUserFromJamboreeEvents(user.toDto(), it.code)
        }
        user.messagingId = messagingId
        val updatedUser = userRepository.save(user)
        jamborees.forEach {
            eventSubscriber.unsubscribeUserFromJamboreeEvents(updatedUser.toDto(), it.code)
        }
        return updatedUser.toDto()
    }

    override fun findByExternalId(externalId: String): BeerSoundUserDto =
            findEntityByExternalId(externalId).toDto()

    override fun findEntityByExternalId(externalId: String): BeerSoundUser =
            userRepository.findByExternalId(externalId)
                    ?: throw EntityNotFoundException("User with externalId = $externalId not found")
}