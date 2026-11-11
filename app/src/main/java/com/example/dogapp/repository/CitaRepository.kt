package com.example.dogapp.repository

import android.content.Context
import com.example.dogapp.data.DogAppDB
import com.example.dogapp.model.Cita
import com.example.dogapp.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitaRepository(val context: Context) {

    private val citaDao = DogAppDB.getDatabase(context).citaDao()
    private val apiService = ApiUtils.getApiService()

    suspend fun saveCita(cita: Cita, messageResponse: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                citaDao.saveCita(cita)
                withContext(Dispatchers.Main) {
                    messageResponse("Cita guardada correctamente")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    messageResponse("Error al guardar la cita: ${e.message}")
                }
            }
        }
    }

    suspend fun getListCitas(): MutableList<Cita> {
        return withContext(Dispatchers.IO) {
            citaDao.getListCitas()
        }
    }

    suspend fun deleteCita(cita: Cita, messageResponse: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                citaDao.deleteCita(cita)
                withContext(Dispatchers.Main) {
                    messageResponse("Cita eliminada correctamente")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    messageResponse("Error al eliminar la cita: ${e.message}")
                }
            }
        }
    }

    suspend fun updateCita(cita: Cita, messageResponse: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                citaDao.updateCita(cita)
                withContext(Dispatchers.Main) {
                    messageResponse("Cita actualizada correctamente")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    messageResponse("Error al actualizar la cita: ${e.message}")
                }
            }
        }
    }

    // Devuelve las razas (keys del Map) ordenadas alfabéticamente
    suspend fun getBreeds(): MutableList<String> {
        return withContext(Dispatchers.IO) {
            apiService.getBreeds().message.keys.sorted().toMutableList()
        }
    }

    // Devuelve la URL de una imagen aleatoria de la raza, o "" si falla
    suspend fun getImageByBreed(breed: String): String {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getImageByBreed(breed).message
            } catch (e: Exception) {
                ""
            }
        }
    }
}
