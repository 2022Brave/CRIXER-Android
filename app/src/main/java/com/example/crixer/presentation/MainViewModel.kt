package com.example.crixer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crixer.data.provider.EmptyCricketDataProvider
import com.example.crixer.data.repository.CricketRepository
import com.example.crixer.domain.validation.CricketDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(val isLoading: Boolean = true, val liveCount: Int = 0, val error: String? = null)

class MainViewModel : ViewModel() {
    private val repository = CricketRepository(EmptyCricketDataProvider(), CricketDataValidator())
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = HomeState(isLoading = true)
            repository.live()
                .onSuccess { matches -> _state.value = HomeState(isLoading = false, liveCount = matches.size) }
                .onFailure { error -> _state.value = HomeState(isLoading = false, error = error.message ?: "Cricket data unavailable") }
        }
    }
}
