package com.beersound.beersoundbackend.entity

import javax.persistence.*

@Entity
@Table(name = "USERS")
data class BeerSoundUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val isHanSolo: Boolean,
        val externalId: String,
        val displayName: String?,
        val pictureUri: String?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "JAMBOREE_ID")
        val jamboree: Jamboree
)