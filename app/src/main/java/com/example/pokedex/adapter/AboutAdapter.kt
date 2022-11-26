package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardAboutBinding
import com.example.pokedex.model.Pokemon

class AboutAdapter : RecyclerView.Adapter<AboutAdapter.AboutViewHolder>() {

    class AboutViewHolder(val binding: CardAboutBinding) : RecyclerView.ViewHolder(binding.root)

    private var listInfo = emptyList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutViewHolder {
        return AboutViewHolder(
            CardAboutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: AboutViewHolder, position: Int) {
        val data = listInfo[position]
        val context = holder.itemView.context

        holder.binding.txtNome.text = data.name.capitalize()
        holder.binding.txtPokeID.text = "#00${data.id}"


        // verificamos se o pokemon tem mais de um tipo
        if (data.types.size > 1) {
            holder.binding.txtType1.text = data.types[0].type.name.capitalize()
            holder.binding.txtType2.text = data.types[1].type.name.capitalize()
        } else {
            holder.binding.txtType1.text = data.types[0].type.name.capitalize()
            holder.binding.txtType2.visibility = View.INVISIBLE
        }

        // utilizamos o Glide para fazer com que o link da imagem png se torne imagem na tela
        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${data.id}.png")
            .into(holder.binding.igPokemon)

        // Aqui iremos trocar a cor do fundo das infos do pokemon de acordo com seu type
        when (data.types[0].type.name) {
            "fire" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.fire
                )
            )
            "bug" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.bug
                )
            )
            "electric" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.electric
                )
            )
            "water" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.water
                )
            )
            "grass" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.grass
                )
            )
            "ice" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.ice
                )
            )
            "normal" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.normal
                )
            )
            "poison" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.poison
                )
            )
            "rock" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.rock
                )
            )
            "dragon" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.dragon
                )
            )
            "psychic" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.psychic
                )
            )
            "dark" -> holder.binding.layoutbg.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    com.example.pokedex.R.color.dark
                )
            )
            else -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        com.example.pokedex.R.color.pink
                    )
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return listInfo.size
    }

    fun setItem(list: Pokemon) {
        listInfo = listOf(list)
        notifyDataSetChanged()
    }
}