package com.example.freetoplay_di.datos.remoto.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConstructorApi @Inject constructor() {

    //Definición de constantes para la configuración de la API
    companion object {
        //URL base de la API
        const val URL_BASE_API = "https://www.freetogame.com/api/"
        //Tiempo de espera para la conexión y lectura (en minutos)
        const val TIEMPO_ESPERA_MINUTOS = 1L
        //Tiempo de espera para la escritura (en segundos)
        const val TIEMPO_ESPERA_ESCRITURA_SEGUNDOS = 15L
    }

    //Método para construir una instancia de la interfaz de la API
    fun <Api> constructor(api: Class<Api>): Api {
        return Retrofit.Builder()
            //Configuración de la URL base
            .baseUrl(URL_BASE_API)
            //Configuración del cliente OkHttpClient
            .client(crearClienteOkHttp())
            //Configuración del convertidor Gson
            .addConverterFactory(GsonConverterFactory.create())
            //Construcción de la instancia de la interfaz API
            .build()
            .create(api)
    }

    //Método para crear un cliente OkHttpClient con configuraciones
    private fun crearClienteOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            //Interceptor para agregar encabezados a cada solicitud
            .addInterceptor { cadena ->
                cadena.proceed(
                    cadena.request().newBuilder()
                        //Configuración de encabezados comunes
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .addHeader("content-type", "application/json")
                        .build()
                )
            }
            //Configuración de tiempos de espera para la conexión, lectura y escritura
            .connectTimeout(TIEMPO_ESPERA_MINUTOS, TimeUnit.MINUTES)
            .readTimeout(TIEMPO_ESPERA_MINUTOS, TimeUnit.MINUTES)
            .writeTimeout(TIEMPO_ESPERA_ESCRITURA_SEGUNDOS, TimeUnit.SECONDS)
            //Construcción del cliente OkHttpClient
            .build()
    }
}
