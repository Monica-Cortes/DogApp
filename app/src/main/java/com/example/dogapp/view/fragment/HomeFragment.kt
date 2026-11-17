package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentHomeBinding
import com.example.dogapp.view.adapter.CitaAdapter
import com.example.dogapp.viewmodel.CitaViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val citaViewModel: CitaViewModel by viewModels()
    private lateinit var citaAdapter: CitaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
    }

    private fun controladores() {
        citaAdapter = CitaAdapter(mutableListOf(), findNavController())
        binding.rvCitas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitas.adapter = citaAdapter

        citaViewModel.listCitas.observe(viewLifecycleOwner) { citas ->
            citaAdapter.setCitas(citas)
            binding.tvSinCitas.visibility = if (citas.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addCitaFragment)
        }

        citaViewModel.getListCitas()
    }
}
