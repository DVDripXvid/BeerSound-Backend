package com.beersound.beersoundbackend.service.implementaion

import com.beersound.beersoundbackend.dto.JamboreeDto
import com.beersound.beersoundbackend.dto.NewJamboreeDto
import com.beersound.beersoundbackend.repository.JamboreeRepository
import com.beersound.beersoundbackend.repository.UserRepository
import com.beersound.beersoundbackend.service.JamboreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class JamboreeServiceImpl @Autowired constructor(
        val jamboreeRepository: JamboreeRepository,
        val userRepository: UserRepository
) : JamboreeService {

    override fun getJamboree(id: Int): JamboreeDto? =
            jamboreeRepository.findById(id).orElse(null)?.toDto()

    override fun createJamboree(externalUserId: String, jamboree: NewJamboreeDto): JamboreeDto {
        val user = userRepository.findByExternalId(externalUserId)
                ?: throw EntityNotFoundException("User with external id = $externalUserId not found")
        val createdJamboree = jamboreeRepository.save(jamboree.toEntity(user))
        return createdJamboree.toDto()
    }

    override fun enterJamboree(externalUserId: String, code: String): JamboreeDto? {
        val user = userRepository.findByExternalId(externalUserId)
                ?: throw EntityNotFoundException("User with external id = $externalUserId not found")

        val jamboree = jamboreeRepository.findByCode(code)

        jamboree?.let {
            if (!jamboree.users.contains(user)) {
                jamboree.users += user
            }
            jamboreeRepository.save(jamboree)
            return jamboree.toDto()
        }

        return null
    }

    override fun getJamboreesByUser(externalUserId: String): List<JamboreeDto> {
        val jamborees = userRepository.findByExternalId(externalUserId)?.jamborees ?: emptyList()
        return jamborees.map { it.toDto() }
    }

}