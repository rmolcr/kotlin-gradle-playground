package com.rmolcr.playground

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun getHealthTest() {
        mockMvc.perform(
            get("/health"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
            .andExpect(content().string("Up and running!"))
    }
}