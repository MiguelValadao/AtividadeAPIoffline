package com.example.rickandmorty.data.remote

import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}
