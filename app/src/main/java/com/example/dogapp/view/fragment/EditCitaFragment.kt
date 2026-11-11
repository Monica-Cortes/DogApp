package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dogapp.databinding.FragmentEditCitaBinding
import com.example.dogapp.viewmodel.CitaViewModel

// TODO HU 5.0: formulario precargado con la cita (Bundle), updateCita() y volver a Home; flecha atrás hacia Detalle.
class EditCitaFragment : Fragment() {

    private lateinit var binding: FragmentEditCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditCitaBinding.inflate(inflater)
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
