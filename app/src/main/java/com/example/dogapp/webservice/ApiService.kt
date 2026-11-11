package com.example.dogapp.webservice

import com.example.dogapp.model.BreedsResponse
import com.example.dogapp.model.DogImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getImageByBreed(@Path("breed") breed: String): DogImageResponse
}
