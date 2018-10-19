package com.beersound.beersoundbackend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtil @Autowired constructor(
        @Value("\${auth.secret}") val secret: String,
        @Value("\${auth.issuer}") val issuer: String,
        @Value("\${auth.token_lifetime}") val tokenLifetime: Int) {

    fun createTokenForUser(userId: String): String {
        val algorithm = Algorithm.HMAC256(secret)
        val expiresAt = Calendar.getInstance()
        expiresAt.add(Calendar.SECOND, tokenLifetime)
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userId)
                .withExpiresAt(expiresAt.time)
                .sign(algorithm)
    }

    fun isTokenValid(token: String): Boolean = verify(token) != null

    fun getUserId(token: String): String? = verify(token)?.subject

    private fun verify(token: String): DecodedJWT? {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
            verifier.verify(token)
        } catch (exception: JWTVerificationException) {
            null
        }
    }
}