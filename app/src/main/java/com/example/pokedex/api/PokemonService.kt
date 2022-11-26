package com.example.pokedex.api

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonsApi
import com.example.pokedex.model.SloTypes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemon(@Query("limit") limit: Int): PokemonsApi

    @GET("pokemon/{id}")
    suspend fun getNumberPokemon(@Path("id") id: Int): Pokemon

    @GET("pokemon/{name}")
    suspend fun getName(@Path("name") name: String): Pokemon
}

