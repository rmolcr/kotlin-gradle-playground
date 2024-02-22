package com.rmolcr.playground.api.rest

import com.rmolcr.playground.service.snyk.SnykService
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@Validated
@RequestMapping("/api/v1")
class SnykController(
        @Autowired
        private val snykService: SnykService
) {

    @PreAuthorize("hasRole('snyk.read')")
    @GetMapping("/snyk/openapi/spec")
    fun openApi(
        @Size(max = 35, message = "Api spec version")
        @NotBlank
        @Pattern(regexp = "^[0-9999-]*\$", message = "Version only allows numbers, chars, - and ~")
        @RequestParam version: String
    ): Any {
        return snykService.getOpenApiSpec(version)
    }
}