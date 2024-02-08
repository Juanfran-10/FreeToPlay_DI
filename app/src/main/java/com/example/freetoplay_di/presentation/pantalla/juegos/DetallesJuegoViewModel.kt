package com.example.freetoplay_di.presentation.pantalla.juegos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freetoplay_di.datos.repositorio.RepositorioBaseImpl
import com.example.freetoplay_di.dominio.modelos.DetallesJuego
import com.example.freetoplay_di.herramientas.GAME_ID_KEY
import com.example.freetoplay_di.herramientas.Recursos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Anotación que indica que DetallesJuegoViewModel es un ViewModel inyectado por Hilt
@HiltViewModel
class DetallesJuegoViewModel @Inject constructor(
    //Repositorio que proporciona acceso a los datos
    private val repositorio: RepositorioBaseImpl,
    //Manejador de estado guardado para retener datos
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //Flujo de estado mutable para los detalles del juego
    private val _detallesJuegoState: MutableStateFlow<Recursos<DetallesJuego?>> =
        MutableStateFlow(value = Recursos.Loading())
    val detallesJuegoState: StateFlow<Recursos<DetallesJuego?>>
        get() = _detallesJuegoState

    //Estado mutable para el título del juego
    private val _tituloJuegoState = mutableStateOf(value = "")
    val tituloJuego: State<String>
        get() = _tituloJuegoState

    //Inicialización del ViewModel
    init {
        //Obtener el identificador del juego almacenado en el estado guardado
        savedStateHandle.get<String>(key = GAME_ID_KEY)?.let { id ->
            //Obtener los detalles del juego utilizando el identificador
            getDetallesJuego(id = id.toInt())
        }
    }

    //Función privada para obtener los detalles del juego por su identificador
    private fun getDetallesJuego(id: Int) {
        viewModelScope.launch {
            //Actualizar el estado de los detalles del juego con el resultado de la llamada al repositorio
            _detallesJuegoState.value = repositorio.obtenerJuego(id = id)
        }
    }

    //Función para establecer el título del juego
    fun setTituloJuego(titulo: String) {
        _tituloJuegoState.value = titulo
    }
}