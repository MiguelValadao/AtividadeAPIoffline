package com.example.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun observeCharacters(): Flow<List<CharacterEntity>>

    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)
}
