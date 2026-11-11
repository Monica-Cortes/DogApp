package com.example.dogapp.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.databinding.ItemCitaBinding
import com.example.dogapp.model.Cita

class CitaViewHolder(
    private val binding: ItemCitaBinding,
    private val navController: NavController
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemCita(cita: Cita) {
        binding.tvNombre.text = cita.nombreMascota
        binding.tvSintoma.text = cita.sintoma
        binding.tvTurno.text = "# ${cita.id}"
        Glide.with(binding.root.context)
            .load(cita.imagenUrl)
            .into(binding.ivMascota)

        binding.cvCita.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", cita)
            navController.navigate(R.id.action_homeFragment_to_detailCitaFragment, bundle)
        }
    }
}
