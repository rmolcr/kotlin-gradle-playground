package com.rmolcr.playground.controller

import com.ninjasquad.springmockk.MockkBean
import com.rmolcr.playground.service.snyk.SnykService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class SnykControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var snykService: SnykService

    @Test
    fun getSnykOpenApiSpecOkTest() {
        every { snykService.getOpenApiSpec(any()) } returns mapOf("foo" to "bar")

        mockMvc.perform(
                get("/api/v1/snyk/openapi/spec?version=2024-01-23")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
    }

    @Test
    fun getSnykOpenApiSpecFailedTest() {
        mockMvc.perform(
                get("/api/v1/snyk/openapi/spec?version=2024-01-23")
                        .with(httpBasic("user2", "user2p")))
                .andExpect(status().isForbidden)
    }
}