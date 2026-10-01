package com.example.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.CharacterRepository
import com.example.rickandmorty.data.local.CharacterEntity
import com.example.rickandmorty.ui.state.SyncState
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters: StateFlow<List<CharacterEntity>> = repository.characters
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    fun syncCharacters() {
        if (_syncState.value is SyncState.Loading) return

        viewModelScope.launch {
            _syncState.value = SyncState.Loading
            try {
                repository.syncCharacters()
                _syncState.value = SyncState.Success
            } catch (e: IOException) {
                _syncState.value = SyncState.Error(
                    "Sem conexão com a internet. Os personagens salvos continuam disponíveis."
                )
            } catch (e: Exception) {
                _syncState.value = SyncState.Error(
                    "Falha na sincronização: ${e.localizedMessage ?: "erro desconhecido"}"
                )
            }
        }
    }

    class Factory(
        private val repository: CharacterRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
                return CharacterViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
