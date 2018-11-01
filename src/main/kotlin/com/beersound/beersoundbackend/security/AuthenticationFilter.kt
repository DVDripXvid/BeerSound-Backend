package com.beersound.beersoundbackend.security

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.repository.UserRepository
import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.model_objects.specification.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Order(1)
class AuthenticationFilter @Autowired constructor(
        private val jwtUtil: JwtUtil,
        private val spotifyApiBuilder: SpotifyApi.Builder,
        private val userRepository: UserRepository,
        @Value("\${auth.header_name}") private val bsHeaderName: String,
        @Value("\${auth.spotify_header_name}") private val spotifyHeaderName: String)
    : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain) {
        val token = request.getHeader(bsHeaderName)
        if (token != null && jwtUtil.isTokenValid(token)) {
            val userId = jwtUtil.getUserId(token)
            request.setAttribute(userAttrName, userId)
            chain.doFilter(request, response)
            return
        }
        val spotifyToken = request.getHeader(spotifyHeaderName)
        if (spotifyToken != null) {
            try {
                val spotifyApi = spotifyApiBuilder
                        .setAccessToken(spotifyToken)
                        .build()
                val user = spotifyApi.currentUsersProfile.build().execute()
                saveUserDetails(user)
                val bsToken = jwtUtil.createTokenForUser(user.id)
                response.addHeader(bsHeaderName, bsToken)
                request.setAttribute(userAttrName, user.id)
            } catch (e: Exception) {
                handleUnauthorized(response, "Cannot obtain user details from Spotify")
                return
            }
            chain.doFilter(request, response)
            return
        }
        handleUnauthorized(response, "You are not welcomed here")
    }

    private fun handleUnauthorized(response: HttpServletResponse, reason: String) {
        response.sendError(401, reason)
    }

    private fun saveUserDetails(spotifyUser: User) {
        val foundUser = userRepository.findByExternalId(spotifyUser.id)
        val bsUser = BeerSoundUser(
                id = foundUser?.id,
                externalId = spotifyUser.id,
                displayName = spotifyUser.displayName ?: spotifyUser.id,
                pictureUri = spotifyUser.images?.firstOrNull()?.url,
                controlledJamborees = mutableListOf(),
                jamborees = emptyList(),
                tracks = mutableListOf(),
                messagingId = null
        )
        userRepository.save(bsUser)
    }

}