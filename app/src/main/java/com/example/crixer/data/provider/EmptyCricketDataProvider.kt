package com.example.crixer.data.provider

import com.example.crixer.domain.data.CricketDataProvider
import com.example.crixer.domain.model.CrixerMatch

class EmptyCricketDataProvider : CricketDataProvider {
    override suspend fun getLiveMatches(): Result<List<CrixerMatch>> = Result.success(emptyList())
    override suspend fun getUpcomingMatches(): Result<List<CrixerMatch>> = Result.success(emptyList())
    override suspend fun getCompletedMatches(): Result<List<CrixerMatch>> = Result.success(emptyList())
}
