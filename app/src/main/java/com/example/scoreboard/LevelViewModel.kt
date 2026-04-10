package com.example.scoreboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val levels: List<VolleyballLevel>) : UiState()
    data class Error(val message: String) : UiState()
}

class LevelViewModel : ViewModel() {
    private val repository = VolleyballRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchLevels()
    }

    private fun fetchLevels() {
        viewModelScope.launch {
            try {
                val fetchedLevels = repository.getLevels()
                _uiState.value = UiState.Success(fetchedLevels)
            }catch (e: Exception) {
                Log.e("LevelViewModel", "Network call failed!", e)
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}

