package com.rmolcr.playground.service

import com.rmolcr.playground.service.snyk.SnykService
import com.rmolcr.playground.service.snyk.client.SnykClientWrapper
import io.snyk.api.OpenAPIApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class SnykServiceTest {
    @Mock
    private lateinit var oaApi: OpenAPIApi;

    @Mock
    private lateinit var snyk: SnykClientWrapper;

    @InjectMocks
    private lateinit var snykService: SnykService;

    @Test
    fun getOpenApiSpecTest(){
        val apiVersion = "2024-01-23"

        Mockito.doReturn(oaApi).`when`(snyk).openAPIApi
        Mockito.`when`(
                snyk.openAPIApi.getAPIVersion(apiVersion)
        ).thenReturn(mapOf("foo" to "bar"))

        val result : Any = snykService.getOpenApiSpec(apiVersion)

        Assertions.assertNotNull(result)
    }
}