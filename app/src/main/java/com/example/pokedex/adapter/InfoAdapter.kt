package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.CardHomeBinding
import com.example.pokedex.model.Pokemon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InfoAdapter(
    val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    class InfoViewHolder(val binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root)

    private var listItensInfo = emptyList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            CardHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val data = listItensInfo[position]

        holder.binding.txtId.text = "#00${data.id}"
        holder.binding.txtNomePokemon.text = data.name.capitalize()

        loadPokemonImage(holder, data)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(data)
        }
    }

    private fun loadPokemonImage(
        holder: InfoViewHolder,
        data: Pokemon
    ) {
        val context = holder.itemView.context
        Glide.with(context).load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${data.id}.png"
        ).into(holder.binding.igPokemon)
    }


    override fun getItemCount(): Int {
        return listItensInfo.size
    }

    fun setItemInfo(list: Pokemon) {
        listItensInfo = listOf(list)
        notifyDataSetChanged()
    }

}