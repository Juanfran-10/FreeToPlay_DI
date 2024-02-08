package com.example.freetoplay_di.herramientas

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.freetoplay_di.dominio.modelos.Juego

fun LazyGridScope.header(
    //Función de extensión para agregar un encabezado en un LazyVerticalGrid o LazyHorizontalGrid
    content: @Composable LazyGridItemScope.() -> Unit
){
    item(
        //Agrega un elemento al LazyGrid con un ancho máximo especificado
        span = { GridItemSpan(maxLineSpan) },
        //Se especifica el contenido del encabezado
        content = content
    )
}

fun List<Juego>.getUrls(): List<String> {
    //Obtiene las URLs de las miniaturas de una lista de juegos seleccionando aleatoriamente 3 elementos
    return takeRandomElements(numberOfElements = 3).map { it.miniatura }
}

fun <T> List<T>.takeRandomElements(numberOfElements: Int): List<T> {
    //Selecciona aleatoriamente un número específico de elementos de una lista
    return if (numberOfElements > size) this
    else asSequence().shuffled().take(numberOfElements).toList()
}

//Extensión inline para NavHostController que permite navegar a una ruta y ejecutar una acción después de la navegación
inline fun NavHostController.navigate(route: String, onNavigate: () -> Unit) {
    //Ejecuta la acción antes de navegar a la ruta especificada
    onNavigate()
    //Navega a la ruta proporcionada
    navigate(route = route)
}