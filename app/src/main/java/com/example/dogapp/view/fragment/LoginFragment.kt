package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentLoginBinding
import com.example.dogapp.viewmodel.CitaViewModel

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
        binding.lavHuella.setOnClickListener {
            mostrarBiometria()
        }
    }

    // Muestra la ventana emergente de huella digital si el dispositivo la soporta
    private fun mostrarBiometria() {
        val biometricManager = BiometricManager.from(requireContext())
        if (biometricManager.canAuthenticate(BIOMETRIC_WEAK) != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(context, getString(R.string.sin_biometria), Toast.LENGTH_LONG).show()
            return
        }

        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Si el usuario cancela no se muestra error; los demás son mensajes del dispositivo
                    if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON &&
                        errorCode != BiometricPrompt.ERROR_USER_CANCELED
                    ) {
                        Toast.makeText(context, errString, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.autenticacion_biometria))
            .setSubtitle(getString(R.string.ingrese_huella))
            .setNegativeButtonText(getString(R.string.cancelar))
            .setAllowedAuthenticators(BIOMETRIC_WEAK)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
