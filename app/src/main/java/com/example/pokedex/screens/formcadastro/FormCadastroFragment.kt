package com.example.pokedex.screens.formcadastro

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentTelaCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastroFragment : Fragment() {

    private lateinit var binding: FragmentTelaCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTelaCadastroBinding.inflate(layoutInflater, container, false)

        setupButtonRegister()

        binding.txtNavigateRegisterToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_formCadastroFragment_to_formLoginFragment)
        }

        return binding.root
    }

    private fun setupButtonRegister() {
        binding.btCadastrar.setOnClickListener {
            val name = binding.txtInputName.text.toString()
            val email = binding.txtInputEmail.text.toString()
            val senha = binding.txtInputSenha.text.toString()
            val repetirSenha = binding.txtInputRepetirSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty() || repetirSenha.isEmpty() || name.isEmpty()) {
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
                            val usuariosMap = hashMapOf(
                                "nome" to name
                            )

                            val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

                            db.collection("Usuários").document(usuarioId).set(usuariosMap)
                                .addOnCompleteListener {
                                    Log.d("db", "Sucesso ao salvar os dados do Usuario")
                                }.addOnFailureListener {

                                }

                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            binding.txtInputEmail.setText("")
                            binding.txtInputSenha.setText("")
                            binding.txtInputRepetirSenha.setText("")
                            binding.txtInputName.setText("")
                        }
                    }.addOnFailureListener { exception ->
                        val mensagemErro = when (exception) {
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
            } else if (repetirSenha != senha) {
                val snackbar =
                    Snackbar.make(it, "As senhas digitadas não coincidem!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
    }
}