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
import com.example.pokedex.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeAdapter(
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private var listItems = emptyList<PokemonResult>()

    class HomeViewHolder(val binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root)

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

        holder.binding.txtNomePokemon.text = data.name.capitalize()
        holder.binding.txtId.text = "#00${position + 1}"

        loadPokemonImage(holder, position)
        saveFavoritePokemon(holder, position)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListenerID(position + 1)
        }
    }

    private fun loadPokemonImage(
        holder: HomeViewHolder,
        position: Int
    ) {
        val context = holder.itemView.context
        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${position + 1}.png")
            .into(holder.binding.igPokemon)
    }

    private fun saveFavoritePokemon(
        holder: HomeViewHolder,
        position: Int
    ) {

        holder.binding.imgFavorite.setOnClickListener {

            holder.binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

//            val idFavorite: MutableMap<String, Any> = HashMap()
//            idFavorite["pokemonsLiked"] = position + 1
//
            val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("FavoritePokemons").document(usuarioId).set(
                mapOf("pokemonsLiked" to position +1)
            )
                .addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar pokemon favorito")
                }.addOnFailureListener {
                    Log.e(TAG,it.toString())
                }
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