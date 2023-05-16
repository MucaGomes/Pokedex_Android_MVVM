package com.example.pokedex.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.AboutAdapter
import com.example.pokedex.adapter.ItemClickListener
import com.example.pokedex.databinding.FragmentAboutBinding
import com.example.pokedex.model.Pokemon

class AboutFragment : Fragment(), ItemClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAboutBinding
    var pokemonSelected: Pokemon? = null
    var pokemonIdSelected: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)

        val aboutAdapter = setAboutAdapter()

        loadData(aboutAdapter)

        return binding.root
    }

    private fun setAboutAdapter(): AboutAdapter {
        val aboutAdapter = AboutAdapter(this)
        binding.rvlPokeInfo.layoutManager = LinearLayoutManager(context)
        binding.rvlPokeInfo.adapter = aboutAdapter
        binding.rvlPokeInfo.setHasFixedSize(true)
        return aboutAdapter
    }

    fun loadData(aboutAdapter : AboutAdapter){

        pokemonSelected = mainViewModel.pokemonSelected
        pokemonIdSelected = mainViewModel.pokemonIdSelected

        // faz uma verificação para que se o item clicado contém um pokemon por nome ou ID
        if(pokemonSelected != null && pokemonIdSelected == null){
            mainViewModel.myPokemonNameResponse.observe(viewLifecycleOwner){ response ->
                if (response != null) {
                    aboutAdapter.setItem(response)
                }
            }
        }
        if (pokemonIdSelected != null) {
            mainViewModel.getPokemonIdData(pokemonIdSelected!!)
            mainViewModel.myPokemonInfoResponse.observe(viewLifecycleOwner){ response ->
                if (response != null) {
                    aboutAdapter.setItem(response)
                }
            }
        }
    }

    // nesse fragment so vamos utilizar o clique para volta a tela inicial por enquanto
    override fun onItemClickListener(pokemon: Pokemon?) {
        TODO("Not yet implemented")
    }

    override fun onItemClickListenerID(pokemon: Int) {
        TODO("Not yet implemented")
    }

    // ao clicar no botao de volta o fragment é desfeito e entra a tela inicial novamente
    override fun onItemClickListenerNavigation() {
        findNavController().navigate(R.id.action_aboutFragment_to_homeFragment)
    }
}