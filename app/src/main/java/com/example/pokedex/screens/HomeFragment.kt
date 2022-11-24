package com.example.pokedex.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
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


        // configuração dos adapters
        val homeAdapter = HomeAdapter()
        binding.rvlPokemonsItens.layoutManager = GridLayoutManager(context, 2)
        binding.rvlPokemonsItens.adapter = homeAdapter
        binding.rvlPokemonsItens.setHasFixedSize(true)

        mainViewModel.myPokemonInfoResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("Opa", response.body().toString())
                homeAdapter.setInfoPoke(response.body()!!.types)
            }
        }

        mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("info: ", response.results.toString())
                homeAdapter.setItem(response.results)
            }
        }

        return binding.root
    }

}