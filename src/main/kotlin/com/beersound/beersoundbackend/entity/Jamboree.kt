package com.beersound.beersoundbackend.entity

import com.beersound.beersoundbackend.dto.JamboreeDto
import javax.persistence.*


@Entity
@Table(name = "JAMBOREES")
data class Jamboree(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var name: String,
        var isPartyTime: Boolean,

        @Column(unique = true)
        var code: String,

        @ManyToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        var hanSolo: BeerSoundUser,

        @OneToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.ALL])
        var currentTrack: BeerSoundTrack?,

        @OneToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.ALL])
        var overrideCurrentTrack: BeerSoundTrack?,

        @OneToMany(
                mappedBy = "jamboree",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL])
        val tracks: MutableList<BeerSoundTrack>,

        @ManyToMany(
                fetch = FetchType.LAZY,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        @JoinTable(
                name = "JAMBOREE_USERS",
                joinColumns = [JoinColumn(name = "JAMBOREE_ID")],
                inverseJoinColumns = [JoinColumn(name = "USER_ID")])
        val users: MutableList<BeerSoundUser>
) {
    fun toDto() = JamboreeDto(
            id!!,
            name,
            code,
            isPartyTime,
            currentTrack?.toDto(),
            overrideCurrentTrack?.toDto(),
            hanSolo.toDto())
}