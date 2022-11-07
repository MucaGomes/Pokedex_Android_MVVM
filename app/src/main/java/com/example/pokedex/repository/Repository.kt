package com.example.pokedex.repository


import com.example.pokedex.api.RetrofitInstance
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonApiResult
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.model.PokemonsApi
import retrofit2.Response


class Repository {

    suspend fun listPokemon(): PokemonsApi {
        return RetrofitInstance.api.listPokemon(151)
    }
}