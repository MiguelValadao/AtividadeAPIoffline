package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.local.CharacterEntity

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        species = species,
        status = status,
        imageUrl = image
    )
}
