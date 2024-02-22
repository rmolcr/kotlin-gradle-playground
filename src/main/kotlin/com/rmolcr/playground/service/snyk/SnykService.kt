package com.rmolcr.playground.service.snyk

import com.rmolcr.playground.service.snyk.client.SnykClientWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["snyk"])
class SnykService {

    @Autowired
    private lateinit var snyk : SnykClientWrapper
    @Cacheable
    fun getOpenApiSpec(version: String) : Any {
        var result : Any = snyk.openAPIApi.getAPIVersion(version)
        // Do some stuff with received data...
        return result
    }
}