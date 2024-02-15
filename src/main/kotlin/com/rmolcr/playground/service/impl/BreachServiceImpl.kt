package com.rmolcr.playground.service.impl

import com.rmolcr.playground.domain.Breach
import com.rmolcr.playground.service.BreachService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.stream.Collectors

@Service
@CacheConfig(cacheNames = ["breaches"])
class BreachServiceImpl (
        @Qualifier("breachRestTemplate")
        private val breachClient: RestTemplate
) : BreachService {

    @Cacheable
    override fun fetch(): List<Breach>? {
        return breachClient.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java,
        ).body?.asList()
    }

    override fun getByDomain(domain: String): List<Breach>? {
        return this.fetch()?.stream()?.filter { it.domain == domain }?.collect(Collectors.toList())
    }

    override fun getVerified(verificationStatus: Boolean): List<Breach>? {
        return this
            .fetch()
            ?.stream()
            ?.filter { it.isVerified == verificationStatus }
            ?.collect(Collectors.toList())
    }

    override fun getByBreachedData(dataType: String): List<Breach>? {
        return this
            .fetch()
            ?.stream()
            ?.filter {
                it.dataClasses.stream().map { e -> e.lowercase() }.collect(Collectors.toList())
                        .contains(dataType.lowercase())
            }
            ?.collect(Collectors.toList())
    }
}