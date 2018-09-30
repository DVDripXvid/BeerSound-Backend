package com.beersound.beersoundbackend.security

import com.wrapper.spotify.SpotifyApi
import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

object AuthenticationFilterTests : Spek({

    val jwtUtil = JwtUtil("test-secret", "test-issuer", 300)
    val spotifyApiBuilder = mockk<SpotifyApi.Builder>()
    val headerName = "test-bs-header"
    val spotifyHeaderName = "test-spotify-header"
    val userId = "test-user"

    Feature("Authentication") {
        val authFilter by memoized { AuthenticationFilter(jwtUtil, spotifyApiBuilder, headerName, spotifyHeaderName) }
        val filterChain by memoized { spyk<MockFilterChain>() }
        val response by memoized { spyk<MockHttpServletResponse>() }
        val request by memoized { spyk<MockHttpServletRequest>() }

        Scenario("Authentication success with BeerSound token") {

            Given("a request with a valid BeerSound token header") {
                every { request.getHeader(headerName) } returns jwtUtil.createTokenForUser(userId)
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
                verify { request.setAttribute(userAttrName, userId) }
            }
        }
    }
})