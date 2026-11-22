package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentAddCitaBinding
import com.example.dogapp.viewmodel.CitaViewModel
import android.widget.Toast
import com.example.dogapp.model.Cita
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import android.graphics.Typeface


class AddCitaFragment : Fragment() {

    private lateinit var binding: FragmentAddCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
    }


    private fun controladores() {
        tituloToolbar()
        regresarHome()
        validarDatos()
        guardarCita()
    }

    private fun tituloToolbar() {
        binding.toolbar.tvTitle.text = getString(R.string.titulo_nueva_cita)
    }

    private fun regresarHome() {
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_addCitaFragment_to_homeFragment)
        }
    }

    private fun validarDatos() {
        val listEditText = listOf(
            binding.etNombreMascota,
            binding.actvRaza,
            binding.etPropietario,
            binding.etTelefono
        )

        for (editText in listEditText) {
            editText.addTextChangedListener {
                val isListFull = listEditText.all {
                    it.text.isNotBlank()
                }
                binding.btnGuardarCita.isEnabled = isListFull

                if (isListFull) {
                    binding.btnGuardarCita.setTypeface(null, Typeface.BOLD)
                } else {
                    binding.btnGuardarCita.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    private fun guardarCita() {
        binding.btnGuardarCita.setOnClickListener {
            if (binding.spSintomas.selectedItemPosition == 0) {
                Snackbar.make(binding.root, getString(R.string.selecciona_sintoma), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.morado))
                    .show()
            } else {
                val cita = Cita(
                nombreMascota = binding.etNombreMascota.text.toString().trim(),
                raza = binding.actvRaza.text.toString().trim().lowercase(),
                nombrePropietario = binding.etPropietario.text.toString().trim(),
                telefono = binding.etTelefono.text.toString().trim(),
                sintoma = binding.spSintomas.selectedItem.toString() )

                binding.btnGuardarCita.isEnabled = false

                citaViewModel.saveCita(cita) { mensaje ->
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addCitaFragment_to_homeFragment)

                }
            }
        }
    }
    private fun observadorViewModel() {
        observerListBreeds()
    }

    private fun observerListBreeds() {
        citaViewModel.getBreeds()
        citaViewModel.listBreeds.observe(viewLifecycleOwner) { listBreeds ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listBreeds)
            binding.actvRaza.setAdapter(adapter)
        }
    }
}