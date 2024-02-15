package com.rmolcr.playground.api.rest

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Breach
import com.rmolcr.playground.service.BreachService
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
class BreachController(
    @Autowired
    private val breachService: BreachService
) {

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/breaches")
    fun getBreaches(): List<Breach>? {
        return breachService.fetch()
    }

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/breaches/domain")
    fun getBreachesByDomain(@RequestParam name: String): List<Breach>? {
        return breachService.getByDomain(name)
    }

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/breaches/data")
    fun getBreachesByDataType(@RequestParam type: String): List<Breach>? {
        return breachService.getByBreachedData(type)
    }

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/breaches/verified")
    fun getVerified(@RequestParam status: Boolean): List<Breach>? {
        return breachService.getVerified(status)
    }
}