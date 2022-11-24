package com.example.pokedex.repository


import com.example.pokedex.api.RetrofitInstance
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonsApi
import com.example.pokedex.model.SloTypes
import retrofit2.Response

class Repository {

    suspend fun listPokemon(): PokemonsApi {
        return RetrofitInstance.api.listPokemon(151)
    }

    suspend fun dataPokemon(id: Int): Response<Pokemon> {
        return RetrofitInstance.api.getNumberPokemon(id)
    }

    suspend fun dataNamePokemon(name: String): Pokemon {
        return RetrofitInstance.api.getName(name)
    }
}