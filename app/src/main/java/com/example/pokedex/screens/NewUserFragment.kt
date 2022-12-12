package com.example.pokedex.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentNewUserBinding

class NewUserFragment : Fragment() {

    private lateinit var binding : FragmentNewUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewUserBinding.inflate(layoutInflater, container, false)

        binding.apply {
            btCadastro.setOnClickListener {
                findNavController().navigate(R.id.action_newUserFragment_to_formCadastroFragment)
            }
            btLogin.setOnClickListener {
                findNavController().navigate(R.id.action_newUserFragment_to_formLoginFragment)
            }
        }

        return binding.root
    }
}