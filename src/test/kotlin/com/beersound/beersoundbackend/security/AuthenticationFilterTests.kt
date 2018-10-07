package com.beersound.beersoundbackend.security

import com.beersound.beersoundbackend.entity.BeerSoundUser
import com.beersound.beersoundbackend.repository.UserRepository
import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.model_objects.specification.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object AuthenticationFilterTests : Spek({

    val jwtUtil = JwtUtil("test-secret", "test-issuer", 300)
    val spotifyApiBuilder = mockk<SpotifyApi.Builder>()
    val headerName = "test-bs-header"
    val spotifyHeaderName = "test-spotify-header"
    val userId = "test-user"
    val mockUser = BeerSoundUser(1, userId, "Mock Joe", null, emptyList(), mutableListOf(), mutableListOf())
    val userRepository = mockk<UserRepository>()
    every { userRepository.findByExternalId(any()) } returns mockUser
    every { userRepository.save<BeerSoundUser>(any()) } returns mockUser

    Feature("Authentication") {
        val authFilter by memoized { AuthenticationFilter(jwtUtil, spotifyApiBuilder, userRepository, headerName, spotifyHeaderName) }
        val filterChain by memoized { spyk<MockFilterChain>() }
        val response by memoized { spyk<MockHttpServletResponse>() }
        val request by memoized { MockHttpServletRequest() }

        Scenario("Authentication success with BeerSound token") {

            Given("a request with a valid BeerSound token header") {
                request.addHeader(headerName, jwtUtil.createTokenForUser(userId))
            }

            When("doFilter called") {
                authFilter.doFilter(request, response, filterChain)
            }

            Then("filter chain should continue without error") {
                verify { filterChain.doFilter(request, response) }
                verify(exactly = 0) { response.sendError(401, any()) }
                verify(exactly = 0) { response.sendError(401) }
            }

            Then("Request should contains the user's id") {
                assertEquals(userId, request.getAttribute(userAttrName))
            }
        }

        Scenario("Authentication success with Spotify token") {

            Given("a request with a valid Spotify token header. no BeerSound token") {
                request.addHeader(spotifyHeaderName, "dummySpotifyToken")
                every { spotifyApiBuilder.setAccessToken(any()) } returns spotifyApiBuilder
                every { spotifyApiBuilder.build().currentUsersProfile.build().execute() } returns
                        User.Builder()
                                .setId(userId)
                                .setDisplayName("Mock Joe")
                                .build()
            }

            When("doFilter called") {
                authFilter.doFilter(request, response, filterChain)
            }

            Then("filter chain should continue without error") {
                verify { filterChain.doFilter(request, response) }
                verify(exactly = 0) { response.sendError(401, any()) }
                verify(exactly = 0) { response.sendError(401) }
            }

            Then("Request should contains the user's id") {
                assertEquals(userId, request.getAttribute(userAttrName))
            }

            Then("Response should contains the BeerSound token header") {
                assertTrue { response.headerNames.contains(headerName) }
            }
        }
    }
})