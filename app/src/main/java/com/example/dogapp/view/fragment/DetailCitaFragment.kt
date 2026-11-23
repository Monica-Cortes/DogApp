package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dogapp.databinding.FragmentDetailCitaBinding
import com.example.dogapp.viewmodel.CitaViewModel
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.model.Cita
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO HU 4.0: mostrar la cita recibida por Bundle ("clave"), FAB eliminar (deleteCita) y FAB editar hacia EditCita.
class DetailCitaFragment : Fragment() {

    private lateinit var binding: FragmentDetailCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()
    private var cita: Cita?=null

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
        cita = arguments?.getSerializable("clave") as? Cita
        controladores()

    }

    private fun controladores() {
        regresarHome()
        cargarDatos()
        eliminarCita()
        editarCita()
    }

    private fun regresarHome() {
        binding.toolbar.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailCitaFragment_to_homeFragment)
        }
    }

    private fun cargarDatos(){
        val cita = cita ?: return
        binding.toolbar.tvTitle.text = cita.nombreMascota
        binding.tvTurno.text = "# ${cita.id}"
        binding.tvRaza.text = cita.raza
        binding.tvSintoma.text = cita.sintoma
        binding.tvPropietario.text = cita.nombrePropietario
        binding.tvTelefono.text = cita.telefono
        Glide.with(this)
            .load(cita.imagenUrl)
            .into(binding.ivMascota)
    }

    private fun eliminarCita() {
        binding.fabEliminar.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.eliminar_cita))
                .setMessage(getString(R.string.confirm))
                .setPositiveButton(getString(R.string.eliminar)) { _, _ ->
                    confirmarEliminacion()
                }
                .setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun confirmarEliminacion() {
        val cita = cita ?: return
        citaViewModel.deleteCita(cita) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailCitaFragment_to_homeFragment)
        }
    }
    private fun editarCita(){
        binding.fabEditar.setOnClickListener {
            val cita = cita ?: return@setOnClickListener
            val bundle = Bundle()
            bundle.putSerializable("clave", cita)
            findNavController().navigate(R.id.action_detailCitaFragment_to_editCitaFragment, bundle)
        }
    }
}
