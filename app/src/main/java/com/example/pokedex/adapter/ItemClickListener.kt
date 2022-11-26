package com.example.pokedex.adapter

import com.example.pokedex.model.Pokemon

interface ItemClickListener {
    fun onItemClickListener(pokemon: Pokemon?)
    fun onItemClickListenerID(pokemon: Int)
}