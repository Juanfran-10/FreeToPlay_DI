package com.example.freetoplay_di.datos.remoto.dto


import com.example.freetoplay_di.dominio.modelos.DetallesJuego
import com.google.gson.annotations.SerializedName

data class DetalleJuegoDto(
    //Descripción del juego
    @SerializedName("description")
    val descripcion: String,

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

    //Requisitos mínimos del sistema para el juego
    @SerializedName("minimum_system_requirements")
    val requisitosMinimosSistema: RequisitosMinimosDelSistemaDto?,

    //Plataforma en la que se puede jugar el juego
    @SerializedName("platform")
    val plataforma: String,

    //Editor del juego
    @SerializedName("publisher")
    val editor: String,

    //Fecha de lanzamiento del juego
    @SerializedName("release_date")
    val fechaLanzamiento: String,

    //Capturas de pantalla del juego
    @SerializedName("screenshots")
    val screenShots: List<ScreenshotDto>,

    //Breve descripción del juego
    @SerializedName("short_description")
    val breveDescripcion: String,

    //Estado actual del juego
    @SerializedName("status")
    val estado: String,

    //Miniatura o imagen representativa del juego
    @SerializedName("thumbnail")
    val miniatura: String,

    //Título del juego
    @SerializedName("title")
    val titulo: String
) {
    //Función para convertir un objeto DetalleJuegoDto a un objeto DetallesJuego
    fun aDetallesJuego(): DetallesJuego {
        return DetallesJuego(
            descripcion,
            desarrollador,
            urlPerfilFreeToGame,
            urlJuego,
            genero,
            id,
            requisitosMinimosSistema,
            plataforma,
            editor,
            fechaLanzamiento,
            screenShots,
            breveDescripcion,
            estado,
            miniatura,
            titulo
        )
    }
}