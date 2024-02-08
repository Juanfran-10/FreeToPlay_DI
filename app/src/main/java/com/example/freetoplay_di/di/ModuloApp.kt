package com.example.freetoplay_di.di

import android.content.Context
import com.example.freetoplay_di.datos.remoto.api.Api
import com.example.freetoplay_di.datos.remoto.api.ConstructorApi
import com.example.freetoplay_di.application.FreeToPlayApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloApp {

    //Proveedor para la instancia de FreeToPlayApplication
    @Singleton
    @Provides
    fun providesFreeToPlayApplication(
        @ApplicationContext app: Context
    ): FreeToPlayApplication {
        //Se proporciona una instancia de FreeToPlayApplication utilizando el contexto de la aplicación
        //Este proveedor es anotado con @Singleton, lo que significa que Dagger almacenará y reutilizará la instancia
        return app as FreeToPlayApplication
    }

    //Proveedor para la instancia de la interfaz Api
    @Singleton
    @Provides
    fun providesApi(apiBuilder: ConstructorApi): Api {
        //Se proporciona una instancia de Api utilizando un constructor personalizado.
        //El parámetro apiBuilder se utiliza para construir la instancia de Api.
        //También está anotado con @Singleton, indicando que la instancia debe ser única en la aplicación.
        return apiBuilder.constructor(Api::class.java)
    }
}