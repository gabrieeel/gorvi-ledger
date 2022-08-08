package com.gorvi.ledger.config

import com.gorvi.ledger.filter.JwtAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.Exception

@Configuration
@EnableWebSecurity
class SecurityConfig(): WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var jwtAuthorizationFilter: JwtAuthorizationFilter

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}