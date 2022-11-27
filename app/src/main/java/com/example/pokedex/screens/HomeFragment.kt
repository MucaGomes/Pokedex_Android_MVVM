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

        // tornamos essas duas variaveis nulas de novo para não ocorrer erro na hora que o fragment for criado
        mainViewModel.pokemonIdSelecionado = null
        mainViewModel.pokemonSelecionado = null

        // configuração dos adapters
        val homeAdapter = HomeAdapter(this)
        binding.rvlPokemonsItens.layoutManager = GridLayoutManager(context, 2)
        binding.rvlPokemonsItens.adapter = homeAdapter
        binding.rvlPokemonsItens.setHasFixedSize(true)

        // aqui faz com que seja lançado uma requisição de todos os pokemons para a api e já
        // implementa eles na tela em forma de grade
        mainViewModel.myPokemonResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("info: ", response.results.toString())
                homeAdapter.setItem(response.results)
            }
        }

        // função que é acionada quando o botão de pesquisar é clicado na pagina inicial
        binding.fbSearch.setOnClickListener {
            // pegamos o texto digitado pelo usuario e tornamos ele em string para manipularmos
            val texto = binding.edtSearch.text.toString().lowercase()

            if (texto.isNotEmpty()) {
                binding.rvlPokemonsItens.visibility = View.INVISIBLE

                animatedLoading()

                // é criado outro adapter para substituir o primeiro em grade ( por isso usamos o
                // visibility , assim que o adapter é invisivel , ele pode ser alterado por outro)
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

                // se nada for digitado , ao clicar no botao de pesquisa ele faz a
                // requisição de todos os pokemons novamente
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

        return binding.root
    }

    private fun listPokemonForName(infoAdapter: InfoAdapter) {
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


    // em breve irei adicionar um sistema de mensagem de erro , para quando o
    // pokemon digitado nao exista ele retorna essa mensagem
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


    // função que faz com que as infos do item do primeiro adapter clicado seja mandado
    // para o proximo fragment
    override fun onItemClickListener(pokemon: Pokemon?) {
        if (binding.rvlSinglePokemon.visibility == View.VISIBLE) {
            mainViewModel.pokemonSelecionado = pokemon
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
        }
    }

    // função que faz com que as infos do item do primeiro adapter clicado seja mandado
    // para o proximo fragment só que com o Id do pokemon selecionado
    override fun onItemClickListenerID(pokemon: Int) {
        if (binding.rvlPokemonsItens.visibility == View.VISIBLE) {
            mainViewModel.pokemonIdSelecionado = pokemon
            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
        }
    }

    // função não utilizada pois não usamos botao de navegar alem dos itens do recyclerview
    override fun onItemClickListenerNavigation() {
        TODO("Not yet implemented")
    }
}