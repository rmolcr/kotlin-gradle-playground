package com.rmolcr.playground.mapper

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.stream.Collectors

@Component
class MessageMapper() {

    fun toMessageDto(message: Message): MessageDTO {
        return MessageDTO(message.text, message.author)
    }

    fun toMessageDtos(messages: List<Message>): List<MessageDTO> {
        return messages.stream().map { m -> toMessageDto(m) }.collect(Collectors.toList())
    }

    fun toMessage(messageDto: MessageDTO): Message {
        return Message(
            text = messageDto.text,
            author = messageDto.author ?: "annonymous"
        )
    }

}