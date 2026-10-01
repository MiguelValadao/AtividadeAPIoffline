package com.example.rickandmorty.data.remote

data class CharacterResponse(
    val info: PageInfo? = null,
    val results: List<CharacterDto> = emptyList()
)

data class PageInfo(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String? = null,
    val prev: String? = null
)

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)
