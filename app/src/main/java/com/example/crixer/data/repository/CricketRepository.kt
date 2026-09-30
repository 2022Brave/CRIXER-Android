package com.example.crixer.data.repository

import com.example.crixer.domain.data.CricketDataProvider
import com.example.crixer.domain.model.CrixerMatch
import com.example.crixer.domain.validation.CricketDataValidator
import com.example.crixer.domain.validation.ValidationResult

class CricketRepository(private val provider: CricketDataProvider, private val validator: CricketDataValidator) {
    suspend fun live(): Result<List<CrixerMatch>> = load { provider.getLiveMatches() }
    suspend fun upcoming(): Result<List<CrixerMatch>> = load { provider.getUpcomingMatches() }
    suspend fun completed(): Result<List<CrixerMatch>> = load { provider.getCompletedMatches() }

    private suspend fun load(request: suspend () -> Result<List<CrixerMatch>>): Result<List<CrixerMatch>> =
        request().mapCatching { matches ->
            val invalidIds = matches.mapNotNull { match ->
                when (validator.validate(match)) {
                    ValidationResult.Valid -> null
                    is ValidationResult.Invalid -> match.id
                }
            }
            if (invalidIds.isNotEmpty()) error("Rejected " + invalidIds.size + " invalid match records")
            matches.distinctBy { it.id }
        }
}
