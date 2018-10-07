package com.beersound.beersoundbackend.entity

import javax.persistence.*

@Entity
@Table(name = "USERS")
data class BeerSoundUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        val isHanSolo: Boolean,
        val externalId: String,
        val displayName: String?,
        val pictureUri: String?,

        @ManyToMany(
                fetch = FetchType.LAZY,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST]
        )
        @JoinTable(
                name = "JAMBOREE_USERS",
                joinColumns = [JoinColumn(name = "USER_ID")],
                inverseJoinColumns = [JoinColumn(name = "JAMBOREE_ID")]
        )
        val jamborees: MutableList<Jamboree>,

        @OneToMany(mappedBy = "jamboree")
        val tracks: MutableList<BeerSoundTrack>
)