package com.example.pokedex.screens

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.HomeAdapter
import com.example.pokedex.adapter.InfoAdapter
import com.example.pokedex.adapter.ItemClickListener
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.model.Pokemon

class HomeFragment : Fragment(), ItemClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mainViewModel.listPokemon()
        mainViewModel.pokemonIdSelected = null
        mainViewModel.pokemonSelected = null

        val homeAdapter = setListPokemonAdapter()
        loadListPokemons(homeAdapter)

        setupFabButtons()
        setupFunctionSearchPokemon(homeAdapter)

        return binding.root
    }

    private fun setupFunctionSearchPokemon(homeAdapter: HomeAdapter) {
        binding.fbSearch.setOnClickListener {
            val texto = binding.edtSearch.text.toString().lowercase()

            if (texto.isNotEmpty()) {
                binding.rvlPokemonsItens.visibility = View.INVISIBLE

                animatedLoading()

                val infoAdapter = InfoAdapter(this)
                binding.rvlSinglePokemon.layoutManager = GridLayoutManager(context, 2)
                binding.rvlSinglePokemon.adapter = infoAdapter
                binding.rvlSinglePokemon.setHasFixedSize(true)

                Handler().postDelayed({
                    binding.imgLoading.visibility = View.INVISIBLE
                    binding.rvlSinglePokemon.visibility = View.VISIBLE
                    mainViewModel.getPokemonNameData(texto)
                    showPokemonForName(infoAdapter)

                }, 1000)
            } else if (texto.isEmpty()) {
                binding.rvlSinglePokemon.visibility = View.INVISIBLE
                binding.rvlPokemonsItens.visibility = View.INVISIBLE
                animatedLoading()
                Handler().postDelayed({
                    binding.imgLoading.visibility = View.INVISIBLE
                    binding.rvlPokemonsItens.visibility = View.VISIBLE
                    mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            Log.d("info: ", response.results.toString())
                            homeAdapter.setItem(response.results)
                        }
                    }
                }, 1000)
            }
        }
    }

    private fun setupFabButtons() {
        binding.fbPerfil.visibility = View.GONE

        var isAllFabVisible = false

        binding.fbOptions.shrink()

        binding.fbOptions.setOnClickListener {
            binding.fbOptions.animate().apply {
                duration = 1000
                rotationBy(360f)
            }

            if (!isAllFabVisible) {
                binding.fbPerfil.visibility = View.VISIBLE
                binding.fbOptions.extend()

                isAllFabVisible = true
            } else {
                binding.fbPerfil.visibility = View.GONE

                binding.fbOptions.shrink()

                isAllFabVisible = false
            }
        }

        binding.fbPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userPerfilFragment2)
        }
    }

    private fun loadListPokemons(homeAdapter: HomeAdapter) {
        mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("info: ", response.results.toString())
                homeAdapter.setItem(response.results)
            }
        }
    }

    private fun setListPokemonAdapter(): HomeAdapter {
        val homeAdapter = HomeAdapter(this)
        binding.rvlPokemonsItens.layoutManager = GridLayoutManager(context, 2)
        binding.rvlPokemonsItens.adapter = homeAdapter
        binding.rvlPokemonsItens.setHasFixedSize(true)
        return homeAdapter
    }

    private fun showPokemonForName(infoAdapter: InfoAdapter) {
        mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("single: ", response.toString())
                infoAdapter.setItemInfo(response)
            }
        }
    }

    // animação que faz com que a logotipo do nosso aplicativo rode em 360º e faça o efeito de "loading"
    private fun animatedLoading() {
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.animate().apply {
            duration = 1000
            rotationBy(360f)
        }
    }

    override fun onItemClickListener(pokemon: Pokemon?) {
        if (binding.rvlSinglePokemon.visibility == View.VISIBLE) {
            mainViewModel.pokemonSelected = pokemon
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
        }
    }

    override fun onItemClickListenerID(pokemon: Int) {
        if (binding.rvlPokemonsItens.visibility == View.VISIBLE) {
            mainViewModel.pokemonIdSelected = pokemon
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
        }
    }

    override fun onItemClickListenerNavigation() {
        TODO("Not yet implemented")
    }
}