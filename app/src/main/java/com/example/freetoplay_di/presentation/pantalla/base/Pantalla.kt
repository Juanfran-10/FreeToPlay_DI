package com.example.freetoplay_di.presentation.pantalla.base

sealed class Pantalla(
    //Ruta asociada a la pantalla
    val ruta: String
) {
    //Objeto que representa la pantalla principal (Home)
    object HomePantalla : Pantalla(ruta = "home")

    //Objeto que representa la pantalla de detalles de juego
    object DetallesJuegoPantalla : Pantalla(ruta = "gameDetail/{id}")

    //Objeto que representa la pantalla de búsqueda con parámetros de modo y filtro
    object SearchPantalla : Pantalla(
        ruta = "search?mode={mode}&filter={filter}"
    )
}
