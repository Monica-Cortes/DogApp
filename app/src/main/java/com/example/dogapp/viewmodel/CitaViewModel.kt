package com.example.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogapp.model.Cita
import com.example.dogapp.repository.CitaRepository
import kotlinx.coroutines.launch

class CitaViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>()
    private val citaRepository = CitaRepository(context)

    private val _listCitas = MutableLiveData<MutableList<Cita>>()
    val listCitas: LiveData<MutableList<Cita>> get() = _listCitas

    private val _listBreeds = MutableLiveData<MutableList<String>>()
    val listBreeds: LiveData<MutableList<String>> get() = _listBreeds

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    // Pide primero la imagen de la raza y luego guarda la cita
    fun saveCita(cita: Cita, message: (String) -> Unit) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                val url = citaRepository.getImageByBreed(cita.raza)
                citaRepository.saveCita(cita.copy(imagenUrl = url), message)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListCitas() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listCitas.value = citaRepository.getListCitas()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun deleteCita(cita: Cita, message: (String) -> Unit) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                citaRepository.deleteCita(cita, message)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    // Pide primero la imagen de la raza y luego actualiza la cita
    fun updateCita(cita: Cita, message: (String) -> Unit) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                val url = citaRepository.getImageByBreed(cita.raza)
                citaRepository.updateCita(cita.copy(imagenUrl = url), message)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getBreeds() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listBreeds.value = citaRepository.getBreeds()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}
