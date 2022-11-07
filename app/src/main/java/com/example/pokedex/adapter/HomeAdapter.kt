package com.example.pokedex.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardHomeBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonApiResult
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.model.PokemonsApi

class HomeAdapter(
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root)

    private var listItems = emptyList<PokemonResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(CardHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val context = holder.itemView.context
        val data = listItems[position]

        //Glide.with(context).load(data2[position].imageUrl).into(holder.binding.igPokemon)

        holder.binding.txtNomePokemon.text = data.name

        Log.d("opa", listItems.size.toString())
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setItem(list: List<PokemonResult>) {
        listItems = list
        notifyDataSetChanged()
    }

}