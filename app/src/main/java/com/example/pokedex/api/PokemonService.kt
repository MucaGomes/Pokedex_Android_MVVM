package com.example.pokedex.api

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonApiResult
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.model.PokemonsApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemon(@Query("limit") limit: Int): PokemonsApi

    @GET("pokemon/{number}")
    suspend fun getNumberPokemon(@Path("number")number: Int): Response<PokemonsApi>
}