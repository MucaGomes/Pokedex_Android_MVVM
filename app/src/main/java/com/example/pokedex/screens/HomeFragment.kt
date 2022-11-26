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

        // configuração dos adapters
        val homeAdapter = HomeAdapter(this)
        binding.rvlPokemonsItens.layoutManager = GridLayoutManager(context, 2)
        binding.rvlPokemonsItens.adapter = homeAdapter
        binding.rvlPokemonsItens.setHasFixedSize(true)

        listPokemons(homeAdapter)

        binding.fbSearch.setOnClickListener {
            mainViewModel.listPokemon()

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
                    listPokemonForName(infoAdapter)

                }, 1000)
            } else {
                binding.rvlSinglePokemon.visibility = View.INVISIBLE
                binding.rvlPokemonsItens.visibility = View.INVISIBLE
                animatedLoading()
                Handler().postDelayed({
                    binding.imgLoading.visibility = View.INVISIBLE
                    binding.rvlPokemonsItens.visibility = View.VISIBLE
                    listPokemons(homeAdapter)

                }, 1000)
            }
        }

        return binding.root
    }

    private fun listPokemonForName(infoAdapter: InfoAdapter) {
        val texto = binding.edtSearch.text.toString().lowercase()

        if (texto.isNotEmpty()) {
            mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    Log.d("info: ", response.toString())
                    infoAdapter.setItemInfo(response)
                }
            }
        }
    }

    private fun animatedLoading() {
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.animate().apply {
            duration = 1000
            rotationBy(360f)
        }
    }


    fun listPokemons(homeAdapter: HomeAdapter) {
        mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("info: ", response.results.toString())
                homeAdapter.setItem(response.results)
            }
        }
    }

    /*private fun errorSearch(homeAdapter: HomeAdapter) {
        animatedLoading()

        Handler().postDelayed({
            binding.imgLoading.visibility = View.INVISIBLE
            Toast.makeText(context, "Pokemon Não Encontrado :(", Toast.LENGTH_LONG).show()
            binding.rvlPokemonsItens.visibility = View.VISIBLE
            listPokemons(homeAdapter)
        }, 1000)
    }

     */

    override fun onItemClickListener(pokemon: Pokemon?) {
        mainViewModel.pokemonSelecionado = pokemon
        findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
    }

    override fun onItemClickListenerID(pokemon: Int) {
        mainViewModel.pokemonIdSelecionado = pokemon
        findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)

    }
}