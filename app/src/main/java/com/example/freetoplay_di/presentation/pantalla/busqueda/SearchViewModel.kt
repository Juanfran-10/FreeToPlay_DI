package com.example.freetoplay_di.presentation.pantalla.busqueda

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freetoplay_di.datos.repositorio.RepositorioBaseImpl
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.herramientas.BROWSER_GAMES
import com.example.freetoplay_di.herramientas.CURRENT_ROUTE_KEY
import com.example.freetoplay_di.herramientas.LATEST_GAMES
import com.example.freetoplay_di.herramientas.PC_GAMES
import com.example.freetoplay_di.herramientas.Recursos
import com.example.freetoplay_di.herramientas.SEARCH_SCREEN_FILTER_KEY
import com.example.freetoplay_di.herramientas.SEARCH_SCREEN_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Anotación que indica que SearchViewModel es un ViewModel inyectado por Hilt
@HiltViewModel
class SearchViewModel @Inject constructor(
    //Repositorio que proporciona acceso a los datos
    private val repositorio: RepositorioBaseImpl,
    //Manejador de estado guardado para retener datos
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //variable para emitir el estado de carga
    private val _isLoading = Channel<Boolean>()
    val isLoading: Flow<Boolean>
        get() = _isLoading.receiveAsFlow()

    //Flujo de estado para los juegos
    private val _juegos: MutableStateFlow<List<Juego>> = MutableStateFlow(value = emptyList())
    val juegos: StateFlow<List<Juego>>
        get() = _juegos

    //Flujo de estado para la consulta de búsqueda
    private val _query: MutableStateFlow<String> = MutableStateFlow(value = "")
    val query: StateFlow<String>
        get() = _query

    //Flujo de estado para el modo de pantalla
    private val _modoPantalla: MutableStateFlow<String> = MutableStateFlow(value = "")
    val modoPantalla: StateFlow<String>
        get() = _modoPantalla

    //Flujo de estado para la visibilidad de la pantalla de búsqueda detallada
    private val _searchDetailVisible: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val searchDetailVisible: StateFlow<Boolean>
        get() = _searchDetailVisible

    //Inicialización del ViewModel
    init {
        //Obtener el modo de pantalla almacenado en el estado guardado
        savedStateHandle.get<String>(key = SEARCH_SCREEN_MODE_KEY)?.let { mode ->
            _modoPantalla.value = mode
        }

        //Obtener el filtro de pantalla almacenado en el estado guardado y realizar acciones en consecuencia
        savedStateHandle.get<String>(key = SEARCH_SCREEN_FILTER_KEY)?.let { filter ->
            when (filter) {
                PC_GAMES -> obtenerJuegosPlataforma(filtro = PC_GAMES)
                BROWSER_GAMES -> obtenerJuegosPlataforma(filtro = BROWSER_GAMES)
                LATEST_GAMES -> obtenerJuegosMasRecientes()
            }
        }
    }

    //Función invocada al realizar una búsqueda
    fun busqueda(juegos: List<Juego>) {
        viewModelScope.launch {
            _isLoading.send(true)
            delay(500)
            //Filtrar juegos basados en la consulta de búsqueda
            _juegos.value =
                juegos.filter { game -> game.titulo.contains(_query.value, ignoreCase = true) }
            _isLoading.send(false)
        }
    }

    //Limpiar la consulta de búsqueda
    fun limpiarConsultaBusqueda() {
        _query.value = ""
    }

    //Establecer la consulta de búsqueda
    fun onQuery(query: String) {
        _query.value = query
    }

    //Mostrar la pantalla de búsqueda detallada
    fun mostrarBusquedaDetallada() {
        _searchDetailVisible.value = true
    }

    //Establecer la ruta actual de navegación en el estado guardado
    fun setRuta(ruta: String) {
        savedStateHandle.set(
            key = CURRENT_ROUTE_KEY,
            value = ruta
        )
    }

    //Obtener juegos por plataforma
    private fun obtenerJuegosPlataforma(filtro: String) {
        viewModelScope.launch {
            _isLoading.send(true)
            //Obtener juegos del repositorio por plataforma
            _juegos.value = when (val respuesta = repositorio.obtenerJuegosPorPlataforma(plataforma = filtro)) {
                is Recursos.Success<*> -> {
                    respuesta.datos ?: emptyList()
                }
                else -> emptyList()
            }
            _isLoading.send(false)
        }
    }

    //Obtener los juegos más recientes
    private fun obtenerJuegosMasRecientes() {
        viewModelScope.launch {
            _isLoading.send(true)
            //Obtener los juegos más recientes del repositorio
            _juegos.value = when (val respuesta = repositorio.ordenarJuegos(criteria = LATEST_GAMES)) {
                is Recursos.Success<*> -> respuesta.datos ?: emptyList()
                else -> emptyList()
            }
            _isLoading.send(false)
        }
    }
}