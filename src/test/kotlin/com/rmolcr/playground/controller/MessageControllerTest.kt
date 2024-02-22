package com.rmolcr.playground.controller

import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message
import com.rmolcr.playground.mapper.MessageMapper
import com.rmolcr.playground.service.impl.MessageServiceImpl
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var messageService: MessageServiceImpl

    @MockkBean
    lateinit var messageMapper: MessageMapper

    @Test
    fun getAllMessagesTest() {
        val message1 = Message("Message text", LocalDateTime.now().toString(),"TestAuthor")
        val message2 = Message("Message text", LocalDateTime.now().toString(), "TestAuthor2")
        val mdto1 = MessageDTO("Message text", "TestAuthor")
        val mdto2 = MessageDTO("Message text", "TestAuthor2")

        every { messageService.fetch() } returns listOf(message1, message2)
        every { messageMapper.toMessageDtos(listOf(message1, message2)) } returns listOf(mdto1, mdto2)

        mockMvc.perform(
                get("/api/v1/messages")
                        .with(httpBasic("user1", "user1p")))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getMessagesByAuthorTest() {
        val message = Message("Message text", LocalDateTime.now().toString(),"TestAuthor")
        val mdto = MessageDTO("Message text", "TestAuthor")

        every { messageService.getByAuthor("TestAuthor") } returns listOf(message)
        every { messageMapper.toMessageDtos(listOf(message)) } returns listOf(mdto)

        mockMvc.perform(
                get("/api/v1/messages/TestAuthor")
                        .with(httpBasic("user1", "user1p"))        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    fun getMessagesByAuthorUnauthorizedUserTest() {
        mockMvc.perform(
                get("/api/v1/messages/TestAuthor")
                        .with(httpBasic("user3", "user3p"))        )
                .andExpect(status().isForbidden)
    }

    @Test
    fun getMessagesByAuthorInvalidUserTest() {
        mockMvc.perform(
                get("/api/v1/messages/TestAuthor")
                        .with(httpBasic("notValidUser", "notValidPassword"))        )
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun storeDefaultMessagesTest() {
        val message = Message("Message text", LocalDateTime.now().toString(),"TestAuthor")

        every { messageService.add(any()) } returns message

        mockMvc.perform(
                post("/api/v1/messages/default")
                        .with(httpBasic("user1", "user1p"))
                        .with(csrf()))
                .andExpect(status().isOk)
    }

    @Test
    fun storeDefaultMessagesUnauthorizedTest() {
        mockMvc.perform(
                post("/api/v1/messages/default")
                        .with(httpBasic("user2", "user2p"))
                        .with(csrf()))
                .andExpect(status().isForbidden)
    }

    @Test
    fun storeMessageTest() {
        val message = Message("Message text", LocalDateTime.now().toString(),"TestAuthor")
        val mdto = MessageDTO("Message text", "TestAuthor")

        every { messageService.add(any()) } returns message
        every { messageMapper.toMessageDto(message) } returns mdto

        mockMvc.perform(
                post("/api/v1/message")
                        .with(httpBasic("user1", "user1p"))
                        .with(csrf())
                        .content(Gson().toJson(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }
}