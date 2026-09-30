package com.example.crixer.domain.model

enum class MatchStatus { LIVE, UPCOMING, COMPLETED, POSTPONED, ABANDONED, UNKNOWN }
data class Team(val id: String, val name: String, val shortName: String)
data class InningsSummary(val teamId: String, val runs: Int?, val wickets: Int?, val overs: Double?, val isVerified: Boolean)
data class CrixerMatch(
    val id: String, val seriesId: String?, val seriesName: String?, val title: String, val format: String?,
    val status: MatchStatus, val team1: Team, val team2: Team, val innings: List<InningsSummary>,
    val scheduledAtEpochMillis: Long?, val sourceTimestampEpochMillis: Long?, val isVerified: Boolean
)
