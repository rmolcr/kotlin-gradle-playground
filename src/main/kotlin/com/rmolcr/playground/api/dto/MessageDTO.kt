package com.rmolcr.playground.api.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size


data class MessageDTO(
        @Pattern(
                regexp = "^[a-zA-Z0-9¿?!¡ ]*\$",
                message = "Message text should match the regex \"^[a-zA-Z0-9¿?!¡ ]*\$\"")
        val text: String,

        @Size(max = 50, message = "Author name size max 50 chars")
        @Pattern(
                regexp = "^[a-zA-Z]*\$",
                message = "Author name only allows chars")
        val author: String?
)