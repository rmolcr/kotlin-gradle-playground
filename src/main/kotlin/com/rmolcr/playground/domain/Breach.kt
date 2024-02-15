package com.rmolcr.playground.domain

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
data class Breach(
    @JsonProperty("Name")
    val name: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Domain")
    val domain: String,
    @JsonProperty("BreachDate")
    val breachDate: String,
    @JsonProperty("PwnCount")
    val pwnCount: Long,
    @JsonProperty("Description")
    val description: String,
    @JsonProperty("IsVerified")
    val isVerified: Boolean,
    @JsonProperty("DataClasses")
    val dataClasses: List<String>
)