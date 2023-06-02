package com.example.pokedex.screens.perfil

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.FavoritePokemonAdapter
import com.example.pokedex.adapter.ItemClickListener
import com.example.pokedex.databinding.FragmentUserPerfilBinding
import com.example.pokedex.model.FavoritePokemon
import com.example.pokedex.model.Pokemon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class UserPerfilFragment : Fragment(), ItemClickListener {

    private lateinit var binding: FragmentUserPerfilBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserPerfilBinding.inflate(layoutInflater, container, false)

        setupButtons()
        showIdFavoritePokemon()

        return binding.root
    }

    private fun showIdFavoritePokemon() {
        val favoriteAdapter = FavoritePokemonAdapter(this)
        binding.rvlFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvlFavorite.adapter = favoriteAdapter
        binding.rvlFavorite.setHasFixedSize(true)

        val documentRefFav = db.collection("FavoritePokemon").document(usuarioId)

        documentRefFav.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {

                val id = snapshot.get("pokemonLiked").toString()
                Log.d(TAG, id)
                mainViewModel.getPokemonNameData(id)
                parentFragment?.viewLifecycleOwner?.let {
                    mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            favoriteAdapter.setItem(response)
                        }
                    }
                }
            } else {
                Log.e(TAG, "Erro ao carregar favoritos")
            }
        }
    }

    private fun setupButtons() {
        binding.apply {
            btLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_userPerfilFragment2_to_newUserFragment)
            }

            imgBackPerfil.setOnClickListener {
                findNavController().navigate(R.id.action_userPerfilFragment2_to_homeFragment)
            }
        }
    }

    private fun sendIdPokemonForAboutFragment() {
        val documentRefFav = db.collection("FavoritePokemon").document(usuarioId)

        documentRefFav.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val idSelected = snapshot.getLong("pokemonLiked").toString()

                lifecycleScope.launchWhenResumed {
                    mainViewModel.pokemonIdSelected = idSelected.toInt()
                    findNavController().navigate(R.id.action_userPerfilFragment2_to_aboutFragment)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNameUserAuth() {
        val documentRef = db.collection("Usuários").document(usuarioId)

        documentRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                val name = snapshot.getString("nome")?.capitalize()
                binding.txtNameUser.text = ("Ola, $name")
            }

        }
    }

    override fun onStart() {
        super.onStart()

        showNameUserAuth()
    }

    override fun onItemClickListener(pokemon: Pokemon?) {
        sendIdPokemonForAboutFragment()
    }

    override fun onItemClickListenerID(id: Int) {

    }

    override fun onItemClickListenerNavigation() {
        TODO("Not yet implemented")
    }
}