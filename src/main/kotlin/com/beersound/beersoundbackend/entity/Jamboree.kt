package com.beersound.beersoundbackend.entity

import com.beersound.beersoundbackend.dto.JamboreeDto
import javax.persistence.*


@Entity
@Table(name = "JAMBOREES")
data class Jamboree(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        val name: String,

        @Column(unique = true)
        val code: String,

        @OneToMany(mappedBy = "jamboree")
        val tracks: MutableList<BeerSoundTrack>,

        @ManyToMany(mappedBy = "jamborees")
        val users: MutableList<BeerSoundUser>
) {
    fun toDto() = JamboreeDto(id!!, name, code)
}