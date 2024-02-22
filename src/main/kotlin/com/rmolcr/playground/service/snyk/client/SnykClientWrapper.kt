package com.rmolcr.playground.service.snyk.client

import io.snyk.api.OpenAPIApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SnykClientWrapper(
    @Value("\${app.snyk.rest.api.token}")
   private val snykApiToken : String
) {

    init {
        io.snyk.infrastructure.ApiClient.apiKeyPrefix["Authorization"] = "token"
        io.snyk.infrastructure.ApiClient.apiKey["Authorization"] = snykApiToken
    }

    val openAPIApi : OpenAPIApi = OpenAPIApi()
//    Add whatever other api client needed...
//    val orgsApi : OrgsApi = OrgsApi()
//    val projectsApi : ProjectsApi = ProjectsApi()
}