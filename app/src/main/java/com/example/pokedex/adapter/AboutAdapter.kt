package com.example.pokedex.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
        setImageTypePokemon(data, holder)

        // Aqui iremos trocar a cor do fundo das infos do pokemon de acordo com seu type
        setTypeNamePokemon(data, position, holder, context)

        // quando o switch for clicado , a imagem do pokemon deixa de ser default e vira a shiny
        setShinyImageSwitch(holder, context, data)

        setStatsAndProgressBars(holder, data)

        holder.binding.imgBack.setOnClickListener {
            itemClickListener.onItemClickListenerNavigation()
        }

        // utilizamos o Glide para fazer com que o link da imagem png se torne imagem na tela
        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${data.id}.png")
            .into(holder.binding.igPokemon)

    }

    private fun setShinyImageSwitch(
        holder: AboutViewHolder,
        context: Context,
        data: Pokemon
    ) {
        holder.binding.swShiny.setOnCheckedChangeListener { compoundButton, ativo ->

            if (ativo) {
                Glide.with(context)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/${data.id}.png")
                    .into(holder.binding.igPokemon)

            } else {
                Glide.with(context)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${data.id}.png")
                    .into(holder.binding.igPokemon)
            }
        }
    }

    private fun setStatsAndProgressBars(
        holder: AboutViewHolder,
        data: Pokemon
    ) {
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

    private fun setTypeNamePokemon(
        data: Pokemon, position: Int, holder: AboutViewHolder, context: Context
    ) {
        when (data.types[position].type.name) {
            "fire" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.fire
                    )
                )
                // e vamos adicionar imagens para cada tipo tbm
                if (data.types[0].type.name == "fire") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.fire)
                }

            }
            "bug" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.bug
                    )
                )
                if (data.types[0].type.name == "bug") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.bug)
                }
            }
            "fairy" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.psychic
                    )
                )
                if (data.types[0].type.name == "fairy") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.fairy)
                }
            }
            "electric" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.electric
                    )
                )
                if (data.types[0].type.name == "electric") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.electric)
                }
            }
            "water" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.water
                    )
                )
                if (data.types[0].type.name == "water") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.water)
                }
            }
            "grass" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.grass
                    )
                )
                if (data.types[0].type.name == "grass") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.grass)
                }
            }
            "ice" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.ice
                    )
                )
                if (data.types[0].type.name == "ice") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.ice)
                }
            }
            "normal" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.normal
                    )
                )
                if (data.types[0].type.name == "normal") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.normal)
                }
            }
            "poison" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.poison
                    )
                )
                if (data.types[0].type.name == "poison") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.poison)
                }
            }
            "rock" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.rock
                    )
                )
                if (data.types[0].type.name == "rock") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.rock)
                }
            }
            "dragon" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.dragon
                    )
                )
                if (data.types[0].type.name == "dragon") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.dragon)
                }
            }
            "psychic" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.psychic
                    )
                )
                if (data.types[0].type.name == "psychic") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.psychic)
                }
            }
            "ground" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.fire
                    )
                )
                if (data.types[0].type.name == "ground") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.ground)
                }
            }
            "dark" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.dark
                    )
                )
                if (data.types[0].type.name == "dark") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.dark)
                }
            }

            "fighting" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.poison
                    )
                )
                if (data.types[0].type.name == "fighting") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.fighting)
                }
            }

            "flying" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.normal
                    )
                )
                if (data.types[0].type.name == "flying") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.flying)
                }
            }

            "ghost" -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.dark
                    )
                )
                if (data.types[0].type.name == "ghost") {
                    holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.ghost)
                }
            }
            else -> {
                holder.binding.layoutbg.setBackgroundColor(
                    ContextCompat.getColor(
                        context, com.example.pokedex.R.color.pink
                    )
                )
            }
        }
    }

    private fun setImageTypePokemon(
        data: Pokemon, holder: AboutViewHolder
    ) {
        if (data.types.size > 1) {
            holder.binding.txtType1.text = data.types[0].type.name.capitalize()
            holder.binding.txtType2.text = data.types[1].type.name.capitalize()
            when (data.types[1].type.name) {
                "fire" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.fire)
                "bug" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.bug)
                "fairy" -> holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.fairy)
                "electric" -> holder.binding.imgType1.setImageResource(com.example.pokedex.R.drawable.electric)
                "water" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.water)
                "grass" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.grass)
                "ice" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.ice)
                "normal" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.normal)
                "poison" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.poison)
                "rock" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.rock)
                "dragon" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.dragon)
                "psychic" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.psychic)
                "ground" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.ground)
                "dark" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.dark)
                "fighting" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.fighting)
                "flying" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.flying)
                "ghost" -> holder.binding.imgType2.setImageResource(com.example.pokedex.R.drawable.ghost)
            }

        } else {
            holder.binding.txtType1.text = data.types[0].type.name.capitalize()
            holder.binding.txtType2.visibility = View.INVISIBLE
            holder.binding.imgType2.visibility = View.INVISIBLE
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