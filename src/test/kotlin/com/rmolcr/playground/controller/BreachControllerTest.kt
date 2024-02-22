package com.rmolcr.playground.controller

import com.ninjasquad.springmockk.MockkBean
import com.rmolcr.playground.fixture.BreachFixture
import com.rmolcr.playground.service.impl.BreachServiceImpl
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class BreachControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var breachService: BreachServiceImpl

    @Test
    fun getAllBreachesOkTest() {
        every { breachService.fetch() } returns BreachFixture.getBreaches(2).asList()

        mockMvc.perform(
                get("/api/v1/breaches")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getAllBreachesFailedTest() {
        mockMvc.perform(
                get("/api/v1/breaches")
                        .with(httpBasic("invalidUser", "invalidPassword")))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun getBreachesByDomainOkTest() {
        every { breachService.getByDomain(any()) } returns arrayListOf(BreachFixture.getDefaultBreach())

        mockMvc.perform(
                get("/api/v1/breaches/domain?name=default domain")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getBreachesByDomainFailedTest() {
        mockMvc.perform(
                get("/api/v1/breaches/domain?name=somedomain")
                        .with(httpBasic("invalidUser", "invalidPassword")))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun getBreachesByDataTypeOkTest() {
        every { breachService.getByBreachedData(any()) } returns arrayListOf(BreachFixture.getDefaultBreach())

        mockMvc.perform(
                get("/api/v1/breaches/data?type=defaultType1")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getBreachesByDataTypeFailedTest() {
        mockMvc.perform(
                get("/api/v1/breaches/data?type=defaultType1")
                        .with(httpBasic("invalidUser", "invalidPassword")))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun getVerifiedBreachesOkTest() {
        every { breachService.getVerified(any()) } returns arrayListOf(BreachFixture.getDefaultBreach())

        mockMvc.perform(
                get("/api/v1/breaches/verified?status=true")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getVerifiedBreachesFailedTest() {
        mockMvc.perform(
                get("/api/v1/breaches/verified?status=true")
                        .with(httpBasic("invalidUser", "invalidPassword")))
                .andExpect(status().isUnauthorized)
    }
}