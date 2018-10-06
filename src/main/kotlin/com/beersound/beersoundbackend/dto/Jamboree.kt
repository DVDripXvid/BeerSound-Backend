package com.beersound.beersoundbackend.dto

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Jamboree")
data class Jamboree(
        @Id val id: Int,
        val name: String
)