package com.example.dogapp.model

// Respuesta de GET breed/{raza}/images/random (message = URL de la imagen)
data class DogImageResponse(
    val message: String,
    val status: String
)
