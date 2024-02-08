package com.example.freetoplay_di.datos.repositorio

import com.example.freetoplay_di.datos.remoto.api.Api
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.dominio.modelos.DetallesJuego
import com.example.freetoplay_di.dominio.repositorio.RepositorioJuego
import com.example.freetoplay_di.herramientas.Recursos
import javax.inject.Inject

class RepositorioBaseImpl @Inject constructor(
    private val api: Api
): RepositorioBase(), RepositorioJuego {

    //Implementación de la función para obtener todos los juegos
    override suspend fun obtenerTodosLosJuegos(): Recursos<List<Juego>> {
        val respuesta = invokeApi {
            api.obtenerTodosLosJuegos()
        }
        return when(respuesta){
            is Recursos.Error -> Recursos.Error(error = respuesta.error)
            is Recursos.Loading -> Recursos.Loading()
            is Recursos.Success -> Recursos.Success(
                data = respuesta.datos?.map { it.aJuego() }?: emptyList()
            )
        }
    }

    //Implementación de la función para obtener los detalles de un juego por su ID
    override suspend fun obtenerJuego(id: Int): Recursos<DetallesJuego?> {
        val respuesta = invokeApi {
            api.obtenerJuego(id = id)
        }
        return when(respuesta){
            is Recursos.Error -> Recursos.Error(error = respuesta.error)
            is Recursos.Loading -> Recursos.Loading()
            is Recursos.Success -> Recursos.Success(
                data = respuesta.datos?.aDetallesJuego()
            )
        }
    }

    //Implementación de la función para obtener juegos por plataforma
    override suspend fun obtenerJuegosPorPlataforma(plataforma: String): Recursos<List<Juego>> {
        val respuesta = invokeApi {
            api.obtenerJuegosPorPlataforma(plataforma = plataforma)
        }
        return when(respuesta){
            is Recursos.Error -> Recursos.Error(error  = respuesta.error)
            is Recursos.Loading -> Recursos.Loading()
            is Recursos.Success -> Recursos.Success(
                data = respuesta.datos?.map { it.aJuego() }?: emptyList()
            )
        }
    }

    //Implementación de la función para ordenar juegos
    override suspend fun ordenarJuegos(criteria: String): Recursos<List<Juego>> {
        val respuesta = invokeApi {
            api.ordenarJuegos(criteria = criteria)
        }
        return when(respuesta){
            is Recursos.Error -> Recursos.Error(error  = respuesta.error)
            is Recursos.Loading -> Recursos.Loading()
            is Recursos.Success -> Recursos.Success(
                data = respuesta.datos?.map { it.aJuego() }?: emptyList()
            )
        }
    }

}