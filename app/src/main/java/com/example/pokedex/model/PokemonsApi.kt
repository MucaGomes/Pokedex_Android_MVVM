package com.example.pokedex.model

data class PokemonsApi(
    val count: Int,
    val next: String?,
    val previous: String?,
    var results: List<PokemonResult>,
)

// para pokemons individuais
data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>,
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType,
)