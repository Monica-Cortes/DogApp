package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dogapp.databinding.FragmentLoginBinding
import com.example.dogapp.viewmodel.CitaViewModel

// TODO HU 1.0: diseño del login (imagen, título con fuente, Lottie de huella) y BiometricPrompt; al autenticar navegar a Home (action_loginFragment_to_homeFragment).
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
    }

    private fun controladores() {
    }
}
