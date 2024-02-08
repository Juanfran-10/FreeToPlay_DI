package com.example.freetoplay_di.datos.repositorio

import com.example.freetoplay_di.herramientas.Recursos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class RepositorioBase {
    //Función genérica para realizar llamadas a la API de forma segura y manejar resultados y errores
    suspend fun <T> invokeApi(
        apiCall: suspend () -> T
    ): Recursos<T> {
        return withContext(Dispatchers.IO) {
            try {
                //Intenta realizar la llamada a la API y devuelve el resultado exitoso
                Recursos.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                //Captura cualquier error que ocurra durante la llamada a la API y devuelve un resultado de error
                Recursos.Error(error = throwable)
            }
        }
    }
}