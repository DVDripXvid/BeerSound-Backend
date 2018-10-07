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

        @ManyToOne(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        val hanSolo: BeerSoundUser,

        @OneToMany(
                mappedBy = "jamboree",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.MERGE, CascadeType.PERSIST])
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
    fun toDto() = JamboreeDto(id!!, name, code, hanSolo.toDto())
}