package com.gorvi.ledger.controller

import com.gorvi.ledger.dto.response.JwtTokenResponseDTO
import com.gorvi.ledger.util.JwtTokenUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("/api/authenticate")
class AuthenticationController(var jwtTokenUtil: JwtTokenUtil) {

    @PostMapping
    fun authenticate(@RequestParam("username") username : String, @RequestParam("password") password : String) : JwtTokenResponseDTO {
        // TODO we need to authenticate the user FOR REAL
        if (username == "test" && password == "test") {
            return JwtTokenResponseDTO(jwtTokenUtil.generateToken())
        }
        throw RuntimeException("Wrong username/password combination")
    }

}