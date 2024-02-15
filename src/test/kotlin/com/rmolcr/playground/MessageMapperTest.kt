package com.rmolcr.playground

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message
import com.rmolcr.playground.mapper.MessageMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MessageMapperTest {
    private val messageMapper = MessageMapper()

    @Test
    fun toMessageDtoTest() {
        val author = "Test Author"
        val message = Message("Sample text message", LocalDateTime.now().toString(), author)

        val result = messageMapper.toMessageDto(message)

        Assertions.assertEquals(message.text, result.text)
        Assertions.assertEquals(message.author, result.author)
    }

    @Test
    fun toMessageDtosTest() {
        val author = "Test Author"
        val message1 = Message("Sample text message1", LocalDateTime.now().toString(), author)
        val message2 = Message("Sample text message2", LocalDateTime.now().toString(), author)

        val result = messageMapper.toMessageDtos(arrayListOf(message1, message2))

        Assertions.assertEquals(result.size, 2)
        Assertions.assertEquals(result[0].author, message1.author)
        Assertions.assertEquals(result[0].text, message1.text)
        Assertions.assertEquals(result[1].author, message2.author)
        Assertions.assertEquals(result[1].text, message2.text)
    }

    @Test
    fun toMessageTest() {
        val author = "Test Author"
        val msgdto = MessageDTO("Sample text message", author)

        val result = messageMapper.toMessage(msgdto)

        Assertions.assertEquals(msgdto.text, result.text)
        Assertions.assertEquals(msgdto.author, result.author)
        Assertions.assertNull(result.createdTimestamp)
    }
}