package com.example.pokedex

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    val usuarioAtual = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        validateCurrentUser()

        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    private fun validateCurrentUser() {
        if (usuarioAtual == null) {
            Handler().postDelayed({
                if ((onBoardingFinished())) {
                    findNavController().navigate(R.id.action_splashFragment_to_newUserFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
            }, 3000)
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    override fun onStart() {
        super.onStart()

        if (usuarioAtual != null) {
            navigateHome()
        }
    }

    private fun navigateHome() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

}