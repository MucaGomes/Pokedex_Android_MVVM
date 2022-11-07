package com.example.pokedex.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.adapter.HomeAdapter
import com.example.pokedex.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mainViewModel.listPokemon()

        // configuração do adapter]
        val adapter = HomeAdapter()
        binding.rvlPokemonsItens.layoutManager = LinearLayoutManager(context)
        binding.rvlPokemonsItens.adapter = adapter
        binding.rvlPokemonsItens.setHasFixedSize(true)

        mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
            // Log.d("Requisicao", response.body().toString())
            if(response != null) {
                adapter.setItem(response.results)
            }
        }

        return binding.root
    }
}