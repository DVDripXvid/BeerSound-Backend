package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.entity.Jamboree
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.JamboreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JamboreeServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val userRepository: UserRepository
) : JamboreeService {

    override fun createJamboree(externalUserId: String, jamboree: NewJamboreeDto): JamboreeDto {
        val user = userRepository.findByExternalId(externalUserId) ?: userRepository.save(
                BeerSoundUser(
                        id = null,
                        externalId = externalUserId,
                        displayName = externalUserId,
                        pictureUri = null,
                        controlledJamborees = mutableListOf(),
                        jamborees = mutableListOf(),
                        tracks = mutableListOf()
                )
        )
        val createdJamboree = jamboreeRepository.save(jamboree.toEntity(user))
        return createdJamboree.toDto()
    }

    override fun enterJamboree(externalUserId: String, code: String): JamboreeDto? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJamboreesByUser(externalUserId: String): List<JamboreeDto> {
        val jamborees = userRepository.findByExternalId(externalUserId)?.jamborees ?: emptyList()
        return jamborees.map { it.toDto() }
    }

}