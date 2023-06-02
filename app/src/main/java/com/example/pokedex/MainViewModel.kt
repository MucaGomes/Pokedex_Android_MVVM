package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonsApi
import com.example.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var nameUser: String = ""
    var pokemonSelected: Pokemon? = null
    var pokemonIdSelected: Int? = null
    var pokemonIdFavorite: String? = null

    private var _PokemonResponse = MutableLiveData<PokemonsApi>()

    val myPokemonResponse: LiveData<PokemonsApi> = _PokemonResponse

    private var _PokemonInfo = MutableLiveData<Pokemon>()

    val myPokemonInfoResponse: LiveData<Pokemon> = _PokemonInfo

    private var _PokeNameResponse = MutableLiveData<Pokemon>()

    val myPokemonNameResponse: LiveData<Pokemon> = _PokeNameResponse

    fun listPokemon() {
        viewModelScope.launch {
            try {
                val response = repository.listPokemon()
                _PokemonResponse.value = response

            } catch (e: Exception) {
                Log.d("Erro: ", e.message.toString())
            }
        }
    }

    fun getPokemonIdData(id: Int?) {
        viewModelScope.launch {
            try {
                val response = repository.dataForIdPokemon(id)
                _PokemonInfo.value = response
            } catch (e: Exception) {
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun getPokemonNameData(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.dataForNamePokemon(name)
                _PokeNameResponse.value = response
            } catch (e: Exception) {
                Log.d("Erro", e.message.toString())
            }
        }
    }

}