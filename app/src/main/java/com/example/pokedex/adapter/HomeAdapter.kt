package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardHomeBinding
import com.example.pokedex.model.*

class HomeAdapter(
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root)

    private var listItems = emptyList<PokemonResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            CardHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = listItems[position]

        /*
        if (position == 0 ) {
            val type1 = listInfoPoke[0]
            holder.binding.txtType.text = type1.type.name.capitalize()
        }
         */

        holder.binding.txtNomePokemon.text = data.name.capitalize()
        holder.binding.txtId.text = "#00${position + 1}"

        val context = holder.itemView.context
        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${position + 1}.png")
            .into(holder.binding.igPokemon)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListenerID(position + 1)
        }

    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setItem(list: List<PokemonResult>) {
        listItems = list
        notifyDataSetChanged()
    }

}