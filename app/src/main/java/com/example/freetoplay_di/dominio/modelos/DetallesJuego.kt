package com.example.freetoplay_di.dominio.modelos

import com.example.freetoplay_di.datos.remoto.dto.RequisitosMinimosDelSistemaDto
import com.example.freetoplay_di.datos.remoto.dto.ScreenshotDto


data class DetallesJuego(
    val descripcion: String,
    val desarrollador: String,
    val urlPerfilFreeToGame: String,
    val urlJuego: String,
    val genero: String,
    val id: Int,
    val requisitosMinimosSistema: RequisitosMinimosDelSistemaDto?,
    val plataforma: String,
    val editor: String,
    val fechaLanzamiento: String,
    val screenShots: List<ScreenshotDto>,
    val breveDescripcion: String,
    val estado: String,
    val miniatura: String,
    val titulo: String
)
