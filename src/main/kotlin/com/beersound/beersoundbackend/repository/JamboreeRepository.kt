package com.beersound.beersoundbackend.repository

import com.beersound.beersoundbackend.dto.Jamboree
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface JamboreeRepository: CrudRepository<Jamboree, Int>