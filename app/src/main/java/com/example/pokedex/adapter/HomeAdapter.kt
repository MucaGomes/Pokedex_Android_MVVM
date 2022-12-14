package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.databinding.CardHomeBinding
import com.example.pokedex.model.*
import com.example.pokedex.screens.perfil.UserPerfilFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeAdapter(
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

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

        holder.binding.imgFavorite.setOnClickListener {
            holder.binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

            val idFavorite = hashMapOf(
                "favorito" to position + 1
            )

            val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("Favorites").document(usuarioId).set(idFavorite)
                .addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar pokemon favorito")
                }.addOnFailureListener {

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