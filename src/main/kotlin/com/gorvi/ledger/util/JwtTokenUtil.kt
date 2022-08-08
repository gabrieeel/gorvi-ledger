package com.gorvi.ledger.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil {

    @Value("\${jwtKey}")
    lateinit var JWT_KEY: String

    fun generateToken() : String {
        var key = Keys.hmacShaKeyFor(JWT_KEY.toByteArray())

        /**
            iss (issuer): Issuer of the JWT
            sub (subject): Subject of the JWT (the user)
            aud (audience): Recipient for which the JWT is intended
            exp (expiration time): Time after which the JWT expires
            nbf (not before time): Time before which the JWT must not be accepted for processing
            iat (issued at time): Time at which the JWT was issued; can be used to determine age of the JWT
            jti (JWT ID): Unique identifier; can be used to prevent the JWT from being replayed (allows a token to be used only once)
         */
        return Jwts.builder()
            .setIssuer("gorvi-ledger")
            .setSubject("1")
            .setIssuedAt(Date())
            .signWith(key)
            .compact()
    }

    fun getSubjectFromToken(jwtToken: String): String {
        var claims = Jwts.parserBuilder().setSigningKey(JWT_KEY.toByteArray()).build().parseClaimsJws(jwtToken)
        return claims.body.subject
    }

}