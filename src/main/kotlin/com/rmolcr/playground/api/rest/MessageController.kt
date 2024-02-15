package com.rmolcr.playground.api.rest

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.mapper.MessageMapper
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

import com.rmolcr.playground.service.impl.MessageServiceImpl
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.RequestBody

@RestController
@Validated
@RequestMapping("/api/v1")
class MessageController(
    val messageService: MessageServiceImpl,
    val messageMapper: MessageMapper) {

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/messages")
    fun getMessages(): List<MessageDTO> {
        return messageMapper.toMessageDtos(messageService.fetch())
    }

    @PreAuthorize("hasRole('message.read')")
    @GetMapping("/messages/{author}")
    fun getMessageByAuthor(
            @PathVariable
            @Size(max = 50, message = "Author name size max 50 chars")
            @Pattern(regexp = "^[a-zA-Z]*\$", message = "Author name only allows chars")
            author: String): List<MessageDTO> {
        return messageMapper.toMessageDtos(messageService.getByAuthor(author))
    }

    @PreAuthorize("hasRole('message.write')")
    @PostMapping("/messages/default")
    fun storeDefaultMessages() {
        messageService.add(MessageDTO("Bye!", "foo"))
        messageService.add(MessageDTO( "Au revoir!", "bar"))
        messageService.add(MessageDTO( "Talogo!", null))
    }

    @PreAuthorize("hasRole('message.write')")
    @PostMapping("/message")
    fun storeMessage(@RequestBody @Valid message: MessageDTO): MessageDTO {
        return messageMapper.toMessageDto(messageService.add(message))
    }
}