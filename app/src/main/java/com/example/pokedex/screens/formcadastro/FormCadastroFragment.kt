package com.example.pokedex.screens.formcadastro

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentTelaCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastroFragment : Fragment() {

    private lateinit var binding: FragmentTelaCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTelaCadastroBinding.inflate(layoutInflater, container, false)

        binding.txtNavigateRegisterToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_formCadastroFragment_to_formLoginFragment)
        }

        binding.btCadastrar.setOnClickListener {
            val email = binding.txtInputEmail.text.toString()
            val senha = binding.txtInputSenha.text.toString()
            val repetirSenha = binding.txtInputRepetirSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty() || repetirSenha.isEmpty()) {
                val snackbar = Snackbar.make(it, "Preencha todos os Campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else if (senha == repetirSenha) {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful) {
                            val snackbar = Snackbar.make(
                                it,
                                "Conta Criada Com Sucesso!",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            binding.txtInputEmail.setText("")
                            binding.txtInputSenha.setText("")
                            binding.txtInputRepetirSenha.setText("")
                        }
                    }.addOnFailureListener { exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com 6 Caracteres no mínimo!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email Válido!"
                        is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Verifique a conexão com a Internet e Tente novamente"
                        else -> "Erro ao Cadastrar usuário!"
                    }
                        val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                }
            }else if (repetirSenha != senha){
                val snackbar = Snackbar.make(it, "As senhas digitadas não coincidem!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
        return binding.root
    }
}