package com.example.pokedex.adapter;

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardFavoritPokemonBinding
import com.example.pokedex.databinding.CardHomeBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResult
import com.google.firebase.firestore.FirebaseFirestore

class FavoritePokemonAdapter(
        val itemClickListener: ItemClickListener
): RecyclerView.Adapter<FavoritePokemonAdapter.FavoritePokemonViewHolder>(){

        private val db = FirebaseFirestore.getInstance()
        private var listItems = emptyList<Pokemon>()

        class FavoritePokemonViewHolder(val binding: CardFavoritPokemonBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): FavoritePokemonViewHolder {
                return FavoritePokemonViewHolder(
                        CardFavoritPokemonBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false
                        )
                )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: FavoritePokemonViewHolder, position: Int) {
                val data = listItems[position]

                holder.binding.txtNomePokemon.text = data.name.capitalize()
                holder.binding.txtId.text = "#00${data.id}"

                loadPokemonImage(holder, data)

                holder.itemView.setOnClickListener {
                        itemClickListener.onItemClickListener(data)
                }
        }

        private fun loadPokemonImage(holder: FavoritePokemonViewHolder, data: Pokemon) {
                val context = holder.itemView.context
                Glide.with(context)
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${data.id}.png")
                        .into(holder.binding.igPokemon)
        }

        override fun getItemCount(): Int = listItems.size

        fun setItem(list: Pokemon) {
                listItems = listOf(list)
                notifyDataSetChanged()
        }
}
