package com.example.pokedex.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.AboutAdapter
import com.example.pokedex.adapter.HomeAdapter
import com.example.pokedex.databinding.FragmentAboutBinding
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.model.Pokemon

class AboutFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAboutBinding
    private var pokemonSelecionado: Pokemon? = null
    private var pokemonIdSelecionado: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)

        val aboutAdapter = AboutAdapter()
        binding.rvlPokeInfo.layoutManager = LinearLayoutManager(context)
        binding.rvlPokeInfo.adapter = aboutAdapter
        binding.rvlPokeInfo.setHasFixedSize(true)


        carregarData(aboutAdapter)

        return binding.root
    }

    fun carregarData(aboutAdapter : AboutAdapter){
        pokemonSelecionado = mainViewModel.pokemonSelecionado
        pokemonIdSelecionado = mainViewModel.pokemonIdSelecionado
        if(pokemonSelecionado != null){
            mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner){ response ->
                if (response != null) {
                    aboutAdapter.setItem(response)
                }
            }
        }else if (pokemonIdSelecionado != null) {
            mainViewModel.getPokemonData(pokemonIdSelecionado!!)
            mainViewModel.myPokemonInfoResponse.observe(viewLifecycleOwner){ response ->
                if (response != null) {
                    aboutAdapter.setItem(response)
                }
            }
        }
    }
}