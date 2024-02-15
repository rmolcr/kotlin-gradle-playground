package com.rmolcr.playground.domain.app

data class AppUserDetails(
        val username: String,
        val password: String,
        val roles: List<String>
)