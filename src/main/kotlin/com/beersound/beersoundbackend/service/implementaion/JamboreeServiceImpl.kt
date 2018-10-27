package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.entity.Jamboree
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.service.JamboreeService
import com.beersound.beersoundbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class JamboreeServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val userService: UserService
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
        return createdJamboree.toDto()
    }

    override fun enterJamboree(externalUserId: String, code: String): JamboreeDto {
        val user = userService.findEntityByExternalId(externalUserId)

        val jamboree = jamboreeRepository.findByCode(code)
                ?: throw EntityNotFoundException("Jamboree with code = $code not found")

        if (!jamboree.users.contains(user)) {
            jamboree.users += user
        }
        jamboreeRepository.save(jamboree)
        return jamboree.toDto()
    }

    override fun getJamboreesByUser(externalUserId: String): List<JamboreeDto> {
        val jamborees = userService.findEntityByExternalId(externalUserId).jamborees
        return jamborees.map { it.toDto() }
    }

    override fun onPlaybackStopped(id: Int): JamboreeDto {
        val jamboree = getJamboreeEntity(id)
        jamboree.isPartyTime = false
        return jamboreeRepository.save(jamboree).toDto()
    }

}