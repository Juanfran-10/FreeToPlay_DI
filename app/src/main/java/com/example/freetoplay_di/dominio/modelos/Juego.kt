package com.example.freetoplay_di.dominio.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Juego(
    val desarrollador: String,
    val urlPerfilFreeToGame: String,
    val urlJuego: String,
    val genero: String,
    val id: Int,
    val plataforma: String,
    val editor: String,
    val fechaLanzamiento: String,
    val breveDescripcion: String,
    val miniatura: String,
    val titulo: String
): Parcelable
