package com.example.pokedex.screens.formlogin

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentTelaLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLoginFragment : Fragment() {

    private lateinit var binding: FragmentTelaLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTelaLoginBinding.inflate(layoutInflater, container, false)

        setupButtonLogin()

        binding.txtNavigateLoginToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_formLoginFragment_to_formCadastroFragment)
        }

        binding.txtForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_formLoginFragment_to_resetPassowrdUserFragment)
        }

        return binding.root
    }

    private fun setupButtonLogin() {
        binding.btLogar.setOnClickListener { view ->
            val email = binding.txtInputEmail.text.toString()
            val senha = binding.txtInputSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(
                    view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { autenticacao ->
                        if (autenticacao.isSuccessful) {
                            Toast.makeText(context, "Login feito com Sucesso!", Toast.LENGTH_SHORT)
                                .show()
                            navigateHome()
                        }
                    }.addOnFailureListener {
                        val snackbar = Snackbar.make(
                            view,
                            "Erro ao fazer login do usuário!",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                    }
            }
        }
    }

    private fun navigateHome() {
        findNavController().navigate(R.id.action_formLoginFragment_to_homeFragment)
    }

}