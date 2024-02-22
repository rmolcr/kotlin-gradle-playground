package com.rmolcr.playground.api.rest

import com.rmolcr.playground.service.snyk.SnykService
import io.snyk.model.ListOrgs200Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.validation.annotation.Validated

@RestController
@Validated
class DemoController() {

    @GetMapping("/health")
    fun demo(): String {
        return "Up and running!"
    }
}