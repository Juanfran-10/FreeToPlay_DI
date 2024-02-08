package com.example.freetoplay_di.dominio.repositorio

import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.dominio.modelos.DetallesJuego
import com.example.freetoplay_di.herramientas.Recursos


interface RepositorioJuego {
    //Función para obtener todos los juegos
    suspend fun obtenerTodosLosJuegos(): Recursos<List<Juego>>

    //Función para obtener los detalles de un juego por su ID
    suspend fun obtenerJuego(id: Int): Recursos<DetallesJuego?>

    //Función para obtener juegos por plataforma
    suspend fun obtenerJuegosPorPlataforma(platform: String): Recursos<List<Juego>>

    //Función para ordenar juegos
    suspend fun ordenarJuegos(criteria: String): Recursos<List<Juego>>
}