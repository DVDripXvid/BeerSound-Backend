package com.beersound.beersoundbackend.entity

import com.beersound.beersoundbackend.dto.BeerSoundUserDto
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class BeerSoundUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        @Column(unique = true)
        val externalId: String,
        val displayName: String,
        val pictureUri: String?,

        var messagingId: String?,

        @ManyToMany(
                mappedBy = "users",
                fetch = FetchType.LAZY)
        val jamborees: List<Jamboree>,

        @OneToMany(
                mappedBy = "hanSolo",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        val controlledJamborees: MutableList<Jamboree>,

        @OneToMany(
                mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST]
        )
        val tracks: MutableList<BeerSoundTrack>
) {
    fun toDto() = BeerSoundUserDto(id!!, externalId, displayName, pictureUri, messagingId)
}