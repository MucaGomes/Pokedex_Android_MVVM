package com.example.pokedex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pokedex.api.PokemonService
import com.example.pokedex.model.*
import com.example.pokedex.repository.Repository
import com.google.firebase.database.core.Repo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pokemonResponseLiveDataObserver: Observer<PokemonsApi>

    @Mock
    lateinit var repository: Repository

    private var testCoroutinesScope = TestCoroutineScope()

    @Before
    fun setUp() {

        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository)
        mainViewModel.myPokemonResponse.observeForever(pokemonResponseLiveDataObserver)
    }

    @Test
    fun `when view model getListPokemon get sucess then set pokemonList`() {
        // arrange
        val results = listOf<PokemonResult>(
            PokemonResult(
                "pokemon 1",
                "www.pokemonteste.com"
            )
        )
        val pokemons = PokemonsApi(200, "", "", results)

        testCoroutinesScope.launch(Dispatchers.Main){
            `when`(repository.listPokemon()).thenReturn(pokemons)
            mainViewModel.listPokemon()

            verify( pokemonResponseLiveDataObserver).onChanged(pokemons)
        }


    }

}