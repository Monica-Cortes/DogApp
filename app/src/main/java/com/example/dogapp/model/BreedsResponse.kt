package com.example.dogapp.model

// Respuesta de GET breeds/list/all
data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
