package com.beersound.beersoundbackend

import com.wrapper.spotify.SpotifyApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BeersoundBackendApplication {

    @Bean
    fun getSpotifyApiBuilder(): SpotifyApi.Builder {
        return SpotifyApi.Builder()
    }

}

fun main(args: Array<String>) {
    runApplication<BeersoundBackendApplication>(*args)
}
