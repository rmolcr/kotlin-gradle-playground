package com.rmolcr.playground

import com.rmolcr.playground.domain.Breach
import com.rmolcr.playground.fixture.BreachFixture

import com.rmolcr.playground.service.impl.BreachServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class BreachServiceTest {
    @Mock
    private lateinit var restTemplate: RestTemplate;

    @InjectMocks
    private lateinit var breachService: BreachServiceImpl;

    @Test
    fun fetchBreachesTest(){
        Mockito.`when`(
            restTemplate.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java)
        ).thenReturn(ResponseEntity.ok(BreachFixture.getBreaches(count = 2)))

        val result : List<Breach>? = breachService.fetch()

        Assertions.assertEquals(result?.size, 2)
    }

    @Test
    fun getVerifiedBreachesTest(){
        Mockito.`when`(
            restTemplate.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java)
        ).thenReturn(
            ResponseEntity.ok(
                arrayOf(
                    BreachFixture.getDefaultBreach(),
                    BreachFixture.getDefaultBreach(verified = false),
                    BreachFixture.getDefaultBreach(name = "test", verified = false)
                )
            )
        )

        val result : List<Breach>? = breachService.getVerified(true)

        Assertions.assertEquals(result?.size, 1)
    }

    @Test
    fun getNotVerifiedBreachesTest(){
        Mockito.`when`(
            restTemplate.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java)
        ).thenReturn(
            ResponseEntity.ok(
                arrayOf(
                    BreachFixture.getDefaultBreach(),
                    BreachFixture.getDefaultBreach(verified = false),
                    BreachFixture.getDefaultBreach(name = "test", verified = false)
                )
            )
        )

        val result : List<Breach>? = breachService.getVerified(false)

        Assertions.assertEquals(result?.size, 2)
    }

    @Test
    fun getBreachesByDomainTest(){
        Mockito.`when`(
            restTemplate.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java)
        ).thenReturn(
            ResponseEntity.ok(
                arrayOf(
                    BreachFixture.getDefaultBreach(),
                    BreachFixture.getDefaultBreach(name = "test", verified = false)
                )
            )
        )

        val result : List<Breach>? = breachService.getByDomain("test domain")

        Assertions.assertEquals(result?.size, 1)
        Assertions.assertEquals(result?.get(0)?.domain, "test domain")
    }

    @Test
    fun getBreachesByDataTypeTest(){
        Mockito.`when`(
            restTemplate.exchange(
                "/breaches",
                HttpMethod.GET,
                null,
                Array<Breach>::class.java)
        ).thenReturn(
            ResponseEntity.ok(
                arrayOf(
                    BreachFixture.getDefaultBreach(),
                    BreachFixture.getDefaultBreach(name = "test", verified = false),
                    BreachFixture.getDefaultBreach(additionalDataType = "PCI")
                )
            )
        )

        val result : List<Breach>? = breachService.getByBreachedData("PCI")

        Assertions.assertEquals(result?.size, 1)
        result?.get(0)?.dataClasses?.contains("PCI")?.let { Assertions.assertTrue(it) }
    }
}