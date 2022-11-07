package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonApiResult
import com.example.pokedex.model.PokemonResult
import com.example.pokedex.model.PokemonsApi
import com.example.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository,
): ViewModel() {

    private var _PokemonResponse = MutableLiveData<PokemonsApi>()

    val myPokemonResponse: LiveData<PokemonsApi> = _PokemonResponse

    fun listPokemon() {
        viewModelScope.launch {
            try {
                val response = repository.listPokemon()
                _PokemonResponse.value = response
            }catch (e: Exception) {
                Log.d("Erro", e.message.toString())
            }
        }
    }

}