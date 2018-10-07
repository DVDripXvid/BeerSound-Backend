package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.entity.BeerSoundUser
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
        val createdJamboree = jamboreeRepository.save(jamboree.toEntity())
        val user = userRepository.findByExternalId(externalUserId)
        if (user == null) {
            userRepository.save(
                    BeerSoundUser(
                            0,
                            true,
                            externalUserId,
                            externalUserId,
                            null,
                            mutableListOf(createdJamboree),
                            mutableListOf()
                    )
            )
        } else {
            user.jamborees.add(createdJamboree)
            userRepository.save(user)
        }
        return createdJamboree.toDto()
    }

    override fun enterJamboree(externalUserId: String, code: String): JamboreeDto {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJamboreesByUser(externalUserId: String): List<JamboreeDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}