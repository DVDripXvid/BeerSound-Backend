package com.beersound.beersoundbackend.dto

data class BeerSoundUser(val id: Int,
                         val isHanSolo: Boolean,
                         val jamboreeId: Boolean,
                         val externalId: String,
                         val displayName: String?,
                         val pictureUri: String?)