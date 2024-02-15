package com.rmolcr.playground.fixture

import com.rmolcr.playground.domain.Breach

class BreachFixture {

    companion object {
        fun getDefaultBreach(
                name: String = "default",
                verified: Boolean = true,
                additionalDataType: String = "additionalDataType"): Breach {
            return Breach(
                    "$name",
                    "$name breach",
                    "$name domain",
                    "1-1-1990",
                    1,
                    "$name description",
                    verified,
                    arrayListOf("${name}Type1", additionalDataType)
            )
        }

        fun getBreaches(count: Int = 3): Array<Breach> {
            var result = arrayOf<Breach>()
            for (i in 1..count) {
                result += getDefaultBreach(name = "breach${i}")
            }
            return result
        }
    }
}