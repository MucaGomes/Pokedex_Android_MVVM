package com.example.pokedex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonsApi(
    @Expose
    @SerializedName("count")
    val count: Int,
    @Expose
    @SerializedName("next")
    val next: String?,
    @Expose
    @SerializedName("previous")
    val previous: String?,
    @Expose
    @SerializedName("results")
    var results: List<PokemonResult>,

)

data class PokemonResult(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("url")
    val url: String,
)





