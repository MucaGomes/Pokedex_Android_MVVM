package com.example.pokedex.screens.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.adapter.HomeAdapter
import com.example.pokedex.databinding.FragmentResetPassowrdUserBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPassowrdUserFragment : Fragment() {

    private lateinit var binding: FragmentResetPassowrdUserBinding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPassowrdUserBinding.inflate(layoutInflater, container, false)

        binding.buttonResetPassoword.setOnClickListener {
            recuperarSenha()
        }

        return binding.root
    }

    private fun recuperarSenha() {
        val email = binding.txtInputResetEmail.text.toString()

        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            Toast.makeText(context, "Enviamos um email de redefinição de senha no email digitado!!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }

    }

}