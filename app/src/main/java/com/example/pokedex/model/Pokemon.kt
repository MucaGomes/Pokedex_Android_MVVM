package com.example.pokedex.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pokemon (
    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("weight")
    val weight: Int,

    @Expose
    @SerializedName("height")
    val height: Int,

    @Expose
    @SerializedName("sprites")
    val sprites: Sprites,

    @Expose
    @SerializedName("stats")
    val stats: List<Stats>,

    @Expose
    @SerializedName("types")
    var types: List<SloTypes>
)

data class Stats (
    @Expose
    @SerializedName("base_stat")
    val baseStat: Int,

    @Expose
    @SerializedName("stat")
    val stat: Stat,
)

data class Stat (
    @Expose
    @SerializedName("name")
    val name: String

        )

data class SloTypes(
    @Expose
    @SerializedName("slot")
    val slot: Long,
    @Expose
    @SerializedName("type")
    val type: Species
)

data class Species (
    @Expose
    @SerializedName("name")
    val name: String
)

data class Sprites (
    @Expose
    @SerializedName("front_default") val frontDefault: String?
)


