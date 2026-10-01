package com.example.rickandmorty.data

import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.local.CharacterEntity
import com.example.rickandmorty.data.remote.RickAndMortyApi
import com.example.rickandmorty.data.remote.toEntity
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao
) {

    val characters: Flow<List<CharacterEntity>> = characterDao.observeCharacters()

    suspend fun syncCharacters() {
        val remoteCharacters = api.getCharacters().results
        characterDao.upsertAll(remoteCharacters.map { it.toEntity() })
    }
}
