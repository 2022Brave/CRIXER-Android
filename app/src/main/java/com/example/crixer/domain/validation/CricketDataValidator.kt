package com.example.crixer.domain.validation

import com.example.crixer.domain.model.CrixerMatch
import com.example.crixer.domain.model.MatchStatus

class CricketDataValidator {
    fun validate(match: CrixerMatch): ValidationResult {
        val errors = buildList {
            if (match.id.isBlank()) add("match id is blank")
            if (match.team1.id.isBlank() || match.team2.id.isBlank()) add("team id is blank")
            if (match.team1.id == match.team2.id) add("teams are identical")
            if (match.title.isBlank()) add("match title is blank")
            match.innings.forEachIndexed { index, innings ->
                if (innings.teamId != match.team1.id && innings.teamId != match.team2.id) add("innings[$index] belongs to an unknown team")
                if (innings.runs != null && innings.runs < 0) add("innings[$index] has negative runs")
                if (innings.wickets != null && innings.wickets !in 0..10) add("innings[$index] has invalid wickets")
                if (innings.overs != null && innings.overs < 0.0) add("innings[$index] has negative overs")
                if (innings.overs != null) {
                    val balls = ((innings.overs * 10.0).toInt() % 10)
                    if (balls !in 0..5) add("innings[$index] has invalid over ball component")
                }
            }
            if (match.status == MatchStatus.UPCOMING && match.innings.any { it.runs != null || it.wickets != null || it.overs != null }) {
                add("upcoming match contains score data")
            }
        }
        return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
    }
}

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Invalid(val errors: List<String>) : ValidationResult
}
