package com.rmolcr.playground.service

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message
import com.rmolcr.playground.mapper.MessageMapper
import io.mockk.mockk

import com.rmolcr.playground.repository.MessageRepository
import com.rmolcr.playground.service.impl.MessageServiceImpl
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.Optional

class DemoServiceTest {
    private val messageRepository = mockk<MessageRepository>()
    private val messageService = MessageServiceImpl(messageRepository, MessageMapper())

    @Test
    fun addMessageTest() {
        val author = "Test Author"
        val message = Message("Sample text message", LocalDateTime.now().toString(), author)
        val input = MessageDTO("Sample text message", author)

        every { messageRepository.save(any()) } returns message

        val result = messageService.add(input)

        verify(exactly = 1) { messageRepository.save(any()) }
        Assertions.assertEquals(input.text, result.text)
        Assertions.assertEquals(input.author, result.author)
    }

    @Test
    fun getMessageByIdTest() {
        val id = 1000L
        val author = "Test Author"
        val message = Message("Sample text message", LocalDateTime.now().toString(), author, id)

        every { messageRepository.findById(any()) } returns Optional.of(message)

        val result = messageService.getById(id)

        verify(exactly = 1) { messageRepository.findById(id) }
        Assertions.assertEquals(message.text, result?.text)
        Assertions.assertEquals(message.author, result?.author)
    }

    @Test
    fun fetchMessagesTest() {
        val author = "Test Author"
        val message = Message("Sample text message", LocalDateTime.now().toString(), author)

        every { messageRepository.findAll() } returns listOf(message)

        val result = messageService.fetch()

        verify(exactly = 1) { messageRepository.findAll() }
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals(message, result[0])
    }

    @Test
    fun fetchMessagesByAuthorTest() {
        val author = "Test Author"
        val message1 = Message("Sample text message", LocalDateTime.now().toString(), author)
        val message2 = Message("Brand new message 2", LocalDateTime.now().toString(), author)

        every { messageRepository.findByAuthor(author) } returns listOf(message1, message2)

        val result = messageService.getByAuthor(author)

        verify(exactly = 1) { messageRepository.findByAuthor(author) }
        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals(message1, result[0])
        Assertions.assertEquals(message2, result[1])
    }
}