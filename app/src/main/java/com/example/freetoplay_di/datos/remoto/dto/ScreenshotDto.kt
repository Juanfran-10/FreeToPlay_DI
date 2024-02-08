package com.example.freetoplay_di.datos.remoto.dto

import com.example.freetoplay_di.dominio.modelos.Screenshot
import com.google.gson.annotations.SerializedName


data class ScreenshotDto(
    //Identificador único
    val id: Int,

    //URL o ruta de la imagen
    @SerializedName("image")
    val imagen: String
) {
    //Función para convertir un objeto ScreenshotDto a un objeto Screenshot
    fun aScreenshot(): Screenshot {
        return Screenshot(
            id,
            imagen
        )
    }
}