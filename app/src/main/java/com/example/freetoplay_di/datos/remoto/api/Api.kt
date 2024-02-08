package com.example.freetoplay_di.datos.remoto.api


import com.example.freetoplay_di.datos.remoto.dto.DetalleJuegoDto
import com.example.freetoplay_di.datos.remoto.dto.JuegoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    //Obtiene todos los juegos disponiblres
    @GET("games")
    suspend fun obtenerTodosLosJuegos(): List<JuegoDto>

    //Obtiene los detalles de un juego específico según su ID
    @GET("game")
    suspend fun obtenerJuego(
        @Query("id") id: Int
    ): DetalleJuegoDto?

    //Obtiene la lista de juegos filtrados por plataforma
    @GET("games")
    suspend fun obtenerJuegosPorPlataforma(
        @Query("platform") plataforma: String
    ): List<JuegoDto>

    //Obtiene la lista de juegos ordenados según un criterio específico
    @GET("games")
    suspend fun ordenarJuegos(
        @Query("sort-by") criteria: String
    ): List<JuegoDto>
}