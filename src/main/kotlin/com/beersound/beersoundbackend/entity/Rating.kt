package com.beersound.beersoundbackend.entity

import javax.persistence.*

@Entity
@Table(name = "RATINGS")
data class Rating(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var value: Int,

        @ManyToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        val user: BeerSoundUser,

        @ManyToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        val track: BeerSoundTrack
)