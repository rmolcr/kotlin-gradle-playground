package com.rmolcr.playground.config

import com.rmolcr.playground.domain.app.AppUserDetails
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

import org.springframework.security.config.annotation.web.invoke

@ConfigurationProperties(prefix = "app.security.local")
data class AppUserList(
        val users: List<AppUserDetails>
)

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
        val appUserList: AppUserList) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeRequests {
                authorize(anyRequest, authenticated)
            }
//            csrf {
//                disable()
//            }
//            formLogin { }
            httpBasic { }
//            logout {
//                logoutUrl = "/"
//            }
        }
        return http.build()
    }

    @Bean
    // In memory user repository
    // DO NOT USE THIS IN PRODUCTION!
    fun userDetailsService(): UserDetailsService {
        val users = appUserList.users.map {
            User.withDefaultPasswordEncoder()
                .username(it.username)
                .password(it.password)
                .roles(*it.roles.toTypedArray())
                .build()
        }
        return InMemoryUserDetailsManager(users)
    }
}