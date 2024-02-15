package com.rmolcr.playground.service

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Message

interface MessageService {
    fun add(msgdto: MessageDTO): Message

    fun fetch(): List<Message>

    fun getById(id: Long): Message?

    fun getByAuthor(author: String): List<Message>
}