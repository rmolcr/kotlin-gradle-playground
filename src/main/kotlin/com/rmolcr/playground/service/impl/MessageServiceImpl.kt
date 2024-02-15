package com.rmolcr.playground.service.impl

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message
import com.rmolcr.playground.mapper.MessageMapper
import org.springframework.stereotype.Service

import com.rmolcr.playground.repository.MessageRepository
import com.rmolcr.playground.service.MessageService
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@Service
class MessageServiceImpl(
        var messageRepository: MessageRepository,
        var messageMapper: MessageMapper) : MessageService {

    override fun add(msgdto: MessageDTO): Message {
        var message = messageMapper.toMessage(msgdto)
        message.createdTimestamp = LocalDateTime.now().toString()
        return messageRepository.save(message)
    }

    override fun getById(id: Long): Message? {
        return messageRepository.findByIdOrNull(id)
    }

    override fun getByAuthor(author: String): List<Message> {
        return messageRepository.findByAuthor(author)
    }

    override fun fetch(): List<Message> {
        return messageRepository.findAll().toList()
    }

}