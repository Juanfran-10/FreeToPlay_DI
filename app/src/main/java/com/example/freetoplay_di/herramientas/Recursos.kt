package com.example.freetoplay_di.herramientas

sealed class Recursos<T>(
    //Clase sellada que encapsula el resultado de una operación asincrónica
    val datos: T? = null,  //Datos exitosos de la operación
    val error: Throwable? = null  //Error en caso de fallo en la operación
) {
    //Clase que representa un resultado exitoso de la operación
    class Success<T>(data: T) : Recursos<T>(datos = data)

    //Clase que representa el estado de carga o procesamiento en curso
    class Loading<T>(data: T? = null) : Recursos<T>(datos = data)

    //Clase que representa un error en la operación
    class Error<T>(data: T? = null, error: Throwable? = null) : Recursos<T>(datos = data, error = error)
}
