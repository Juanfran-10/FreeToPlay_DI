package com.example.freetoplay_di.datos.remoto.dto

import com.example.freetoplay_di.dominio.modelos.RequerimientosMinimosDelSistema
import com.google.gson.annotations.SerializedName


data class RequisitosMinimosDelSistemaDto(
    //Tarjeta gráfica requerida
    @SerializedName("graphics")
    val graficos: String,

    //Memoria mínima requerida
    @SerializedName("memory")
    val memoria: String,

    //Sistema operativo requerido
    @SerializedName("os")
    val sistemaOperativo: String,

    //Procesador mínimo requerido
    @SerializedName("proccesor")
    val procesador: String,

    //Almacenamiento mínimo requerido
    @SerializedName("storage")
    val almacenamiento: String
) {
    //Función para convertir un objeto RequisitosMinimosDelSistemaDto a un objeto RequerimientosMinimosDelSistema
    fun aRequerimientosMinimosDelSistema(): RequerimientosMinimosDelSistema {
        return RequerimientosMinimosDelSistema(
            graficos,
            memoria,
            sistemaOperativo,
            procesador,
            almacenamiento
        )
    }
}