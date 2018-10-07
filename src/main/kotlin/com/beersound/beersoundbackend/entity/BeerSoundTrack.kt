package com.beersound.beersoundbackend.entity

import javax.persistence.*

@Entity
@Table(name = "TRACKS")
data class BeerSoundTrack(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        val sequenceNumber: Int,
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "JAMBOREE_ID")
        val jamboree: Jamboree,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "USER_ID")
        val user: BeerSoundUser
)
