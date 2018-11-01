package com.beersound.beersoundbackend.entity

import com.beersound.beersoundbackend.dto.BeerSoundTrackDto
import javax.persistence.*

@Entity
@Table(name = "TRACKS")
data class BeerSoundTrack(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var sequenceNumber: Int,
        val externalId: String,
        val title: String,
        val artist: String,
        val album: String,
        val durationInMs: Int,
        val albumImageUrl: String?,

        @ManyToOne(
                fetch = FetchType.LAZY,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        val jamboree: Jamboree,

        @ManyToOne(
                fetch = FetchType.LAZY,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        val user: BeerSoundUser,

        @OneToMany(
                mappedBy = "track",
                fetch = FetchType.EAGER,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE])
        val ratings: MutableList<Rating>
) {
    fun toDto() = BeerSoundTrackDto(id!!, sequenceNumber, externalId, title, artist, album, durationInMs, albumImageUrl)
}
