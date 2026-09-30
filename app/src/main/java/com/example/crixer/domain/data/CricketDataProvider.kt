package com.example.crixer.domain.data

import com.example.crixer.domain.model.CrixerMatch

interface CricketDataProvider {
    suspend fun getLiveMatches(): Result<List<CrixerMatch>>
    suspend fun getUpcomingMatches(): Result<List<CrixerMatch>>
    suspend fun getCompletedMatches(): Result<List<CrixerMatch>>
}
