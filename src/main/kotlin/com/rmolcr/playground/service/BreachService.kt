package com.rmolcr.playground.service

import com.rmolcr.playground.api.dto.MessageDTO
import com.rmolcr.playground.domain.Breach
import com.rmolcr.playground.domain.Message

interface BreachService {
    fun fetch(): List<Breach>?

    fun getByDomain(domain: String): List<Breach>?

    fun getVerified(verificationStatus: Boolean): List<Breach>?

    fun getByBreachedData(dataType: String): List<Breach>?
}