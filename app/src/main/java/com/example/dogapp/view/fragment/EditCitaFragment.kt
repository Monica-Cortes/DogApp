package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentEditCitaBinding
import com.example.dogapp.model.Cita
import com.example.dogapp.viewmodel.CitaViewModel
import android.graphics.Typeface
import androidx.core.widget.addTextChangedListener

class EditCitaFragment : Fragment() {

    private lateinit var binding: FragmentEditCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()
    private var cita: Cita?=null

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
        cita = arguments?.getSerializable("clave") as? Cita
        controladores()

    }

    private fun controladores() {
        regresarDetalle()
        cargarDatos()
        editarDatos()
        cargarRazas()
        validarDatos()
    }

    private fun validarDatos() {
        val listEditText = listOf(
            binding.etNombreMascota,
            binding.actvRaza,
            binding.etPropietario,
            binding.etTelefono
        )

        // Habilita o deshabilita el botón según si todos los campos tienen texto
        fun actualizarBoton() {
            val isListFull = listEditText.all { it.text.isNotBlank() }
            binding.btnEditarCita.isEnabled = isListFull

            if (isListFull) {
                binding.btnEditarCita.setTypeface(null, Typeface.BOLD)
            } else {
                binding.btnEditarCita.setTypeface(null, Typeface.NORMAL)
            }
        }

        for (editText in listEditText) {
            editText.addTextChangedListener { actualizarBoton() }
        }

        // Evalúa una vez al inicio, porque el formulario llega precargado
        actualizarBoton()
    }

    private fun cargarDatos() {
        val cita = cita ?: return
        binding.toolbar.tvTitle.text = getString(R.string.titulo_editar_cita)
        binding.etNombreMascota.setText(cita.nombreMascota)
        binding.actvRaza.setText(cita.raza, false)
        binding.etPropietario.setText(cita.nombrePropietario)
        binding.etTelefono.setText(cita.telefono)
    }

    private fun cargarRazas() {
        citaViewModel.getBreeds()
        citaViewModel.listBreeds.observe(viewLifecycleOwner) { listBreeds ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                listBreeds
            )
            binding.actvRaza.setAdapter(adapter)
        }
    }

    private fun regresarDetalle() {
        binding.toolbar.ivBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", cita)
            findNavController().navigate(R.id.action_editCitaFragment_to_detailCitaFragment, bundle)
        }
    }
    private fun editarDatos() {
        binding.btnEditarCita.setOnClickListener {
            val citaOriginal = cita ?: return@setOnClickListener

            val citaEditada = citaOriginal.copy(
                nombreMascota = binding.etNombreMascota.text.toString().trim(),
                raza = binding.actvRaza.text.toString().trim().lowercase(),
                nombrePropietario = binding.etPropietario.text.toString().trim(),
                telefono = binding.etTelefono.text.toString().trim()
            )

            citaViewModel.updateCita(citaEditada) { mensaje ->
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editCitaFragment_to_homeFragment)
            }
        }
    }


}
