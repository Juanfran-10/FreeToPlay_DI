package com.example.freetoplay_di.datos.remoto.dto

import com.example.freetoplay_di.dominio.modelos.Juego
import com.google.gson.annotations.SerializedName

data class JuegoDto(
    //Desarrollador del juego
    @SerializedName("developer")
    val desarrollador: String,

    //URL del perfil del juego en FreeToGame
    @SerializedName("freetogame_profile_url")
    val urlPerfilFreeToGame: String,

    //URL del juego
    @SerializedName("game_url")
    val urlJuego: String,

    //Género del juego
    @SerializedName("genre")
    val genero: String,

    //Identificador único del juego
    val id: Int,

    //Plataforma en la que se puede jugar el juego
    @SerializedName("platform")
    val plataforma: String,

    //Editor del juego
    @SerializedName("publisher")
    val editor: String,

    //Fecha de lanzamiento del juego
    @SerializedName("release_date")
    val fechaLanzamiento: String,

    //Breve descripción del juego
    @SerializedName("short_description")
    val breveDescripcion: String,

    //Miniatura del juego
    @SerializedName("thumbnail")
    val miniatura: String,

    //Título del juego
    @SerializedName("title")
    val titulo: String
) {
    //Función para convertir un objeto JuegoDto a un objeto Juego
    fun aJuego(): Juego {
        return Juego(
            desarrollador,
            urlPerfilFreeToGame,
            urlJuego,
            genero,
            id,
            plataforma,
            editor,
            fechaLanzamiento,
            breveDescripcion,
            miniatura,
            titulo
        )
    }
}