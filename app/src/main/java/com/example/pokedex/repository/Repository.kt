package com.example.pokedex.repository


import com.example.pokedex.api.RetrofitInstance
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonsApi

class Repository {

    suspend fun listPokemon(): PokemonsApi {
        return RetrofitInstance.api.listPokemon(200)
    }

    suspend fun dataForIdPokemon(id: Int): Pokemon {
        return RetrofitInstance.api.getDataforIdPokemon(id)
    }

    suspend fun dataForNamePokemon(name: String): Pokemon {
        return RetrofitInstance.api.getDataforNamePokemon(name)
    }
}