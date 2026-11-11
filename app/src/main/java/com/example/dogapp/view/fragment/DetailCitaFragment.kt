package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dogapp.databinding.FragmentDetailCitaBinding
import com.example.dogapp.viewmodel.CitaViewModel

// TODO HU 4.0: mostrar la cita recibida por Bundle ("clave"), FAB eliminar (deleteCita) y FAB editar hacia EditCita.
class DetailCitaFragment : Fragment() {

    private lateinit var binding: FragmentDetailCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCitaBinding.inflate(inflater)
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
