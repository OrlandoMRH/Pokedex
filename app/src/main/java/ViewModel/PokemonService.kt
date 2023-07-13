package ViewModel

import Model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonService {

    /*  SERVICIO GET PARA CONSULTAR Y RETORNAR UN POKEMON   */
    @GET
    suspend fun getPokemonFromAPI(@Url url:String): Response<PokemonModel>
}