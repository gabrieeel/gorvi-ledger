package com.gorvi.ledger.filter

import com.gorvi.ledger.util.JwtTokenUtil
import io.jsonwebtoken.JwtException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.RuntimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class JwtAuthorizationFilter(val jwtTokenUtil: JwtTokenUtil): OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, resp: HttpServletResponse, chain: FilterChain) {
        var authenticationHeader = req.getHeader("Authorization")

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            val jwtToken: String = authenticationHeader.replace("Bearer ", "")
            try {
                val auth = UsernamePasswordAuthenticationToken(jwtTokenUtil.getSubjectFromToken(jwtToken), null, ArrayList())
                SecurityContextHolder.getContext().authentication = auth;
            } catch (e: JwtException) {
                print(e)
                throw RuntimeException(e)
            }
        }

        chain.doFilter(req, resp);
    }
}