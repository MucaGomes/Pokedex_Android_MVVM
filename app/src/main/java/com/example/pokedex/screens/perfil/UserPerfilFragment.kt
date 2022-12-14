package com.example.pokedex.screens.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.AboutAdapter
import com.example.pokedex.adapter.HomeAdapter
import com.example.pokedex.adapter.InfoAdapter
import com.example.pokedex.adapter.ItemClickListener
import com.example.pokedex.databinding.FragmentUserPerfilBinding
import com.example.pokedex.model.Pokemon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.getField
import java.util.concurrent.Flow
import javax.annotation.Nullable

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

        binding.apply {
            btLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_userPerfilFragment2_to_newUserFragment)
            }

            imgBackPerfil.setOnClickListener {
                findNavController().navigate(R.id.action_userPerfilFragment2_to_homeFragment)
            }
        }

        val favoriteAdapter = InfoAdapter(this)
        binding.rvlFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvlFavorite.adapter = favoriteAdapter
        binding.rvlFavorite.setHasFixedSize(true)

        val documentRefFav = db.collection("Favorites").document(usuarioId)

        documentRefFav.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {

                val id = snapshot.getLong("favorito").toString()

                mainViewModel.getPokemonNameData(id)

                parentFragment?.viewLifecycleOwner?.let {
                    mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            favoriteAdapter.setItemInfo(response)
                        }
                    }
                }
            }

        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val documentRef = db.collection("Usuários").document(usuarioId)

        documentRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (snapshot != null) {
                binding.txtNameUser.text = snapshot.getString("nome")
            }

        }



    }

    override fun onItemClickListener(pokemon: Pokemon?) {
        TODO("Not yet implemented")
    }

    override fun onItemClickListenerID(id: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemClickListenerNavigation() {
        TODO("Not yet implemented")
    }
}