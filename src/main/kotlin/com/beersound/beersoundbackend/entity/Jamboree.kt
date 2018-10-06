package com.beersound.beersoundbackend.entity

import javax.persistence.*


@Entity
@Table(name = "JAMBOREES")
data class Jamboree(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val name: String,

        @Column(unique = true)
        val code: String,

        @OneToMany(mappedBy = "jamboree")
        val tracks: List<BeerSoundTrack>,

        @OneToMany(mappedBy = "jamboree")
        val users: List<BeerSoundUser>
)