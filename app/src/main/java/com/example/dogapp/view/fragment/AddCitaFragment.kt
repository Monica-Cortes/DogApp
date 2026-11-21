package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.databinding.FragmentAddCitaBinding
import com.example.dogapp.viewmodel.CitaViewModel
import com.example.dogapp.R
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ArrayAdapter

// TODO HU 3.0: formulario (TextInputLayout, AutoComplete de razas con getBreeds(), Spinner de síntomas), validación del botón y saveCita().
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
    }

    private fun controladores() {

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar.root) { vista, insets ->
            val barraEstado = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            vista.setPadding(vista.paddingLeft, barraEstado.top, vista.paddingRight, vista.paddingBottom)
            insets
        }

        binding.toolbar.tvTitle.text = getString(R.string.titulo_nueva_cita)

        binding.toolbar.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_addCitaFragment_to_homeFragment)
        }


        citaViewModel.listBreeds.observe(viewLifecycleOwner) { razas ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, razas)
            binding.actvRaza.setAdapter(adapter)
        }
        citaViewModel.getBreeds()




    }
}
