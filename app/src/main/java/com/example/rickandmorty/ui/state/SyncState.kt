package com.example.rickandmorty.ui.state

sealed interface SyncState {

    data object Idle : SyncState

    data object Loading : SyncState

    data object Success : SyncState

    data class Error(val message: String) : SyncState
}
