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

class AboutAdapter(
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<AboutAdapter.AboutViewHolder>() {

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
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${data.id}.png")
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

        holder.binding.imgBack.setOnClickListener {
            itemClickListener.onItemClickListenerNavigation()

        }

        // editando os stats do pokemon
        holder.binding.txStatHp.text = data.stats[0].baseStat.toString()
        holder.binding.txtStatAttack.text = data.stats[1].baseStat.toString()
        holder.binding.txtStatDefense.text = data.stats[2].baseStat.toString()
        holder.binding.txtStatSpecialAttk.text = data.stats[3].baseStat.toString()
        holder.binding.txtStatSpecialDef.text = data.stats[4].baseStat.toString()
        holder.binding.txtStatSpeed.text = data.stats[5].baseStat.toString()
        holder.binding.txtStatWeight.text = data.weight.toString()

        // ^ obs: os status vem em lista de 1 a 6 da api , quando esta no kotlin vira um arrayList
        // por isso colocamos do 0 ao 6

        // editando as progressBar de acordo com os stats
        holder.binding.pbHp.setProgress(data.stats[0].baseStat)
        holder.binding.pbAtk.setProgress(data.stats[1].baseStat)
        holder.binding.pbDef.setProgress(data.stats[2].baseStat)
        holder.binding.pbSpecialatk.setProgress(data.stats[3].baseStat)
        holder.binding.pbSpecialDef.setProgress(data.stats[4].baseStat)
        holder.binding.pbSpeed.setProgress(data.stats[5].baseStat)
        holder.binding.pbWeight.setProgress(data.weight)

    }

    override fun getItemCount(): Int {
        return listInfo.size
    }

    fun setItem(list: Pokemon) {
        listInfo = listOf(list)
        notifyDataSetChanged()
    }
}