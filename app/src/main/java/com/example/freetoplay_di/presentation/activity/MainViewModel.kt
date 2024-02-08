package com.example.freetoplay_di.presentation.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freetoplay_di.datos.repositorio.RepositorioBaseImpl
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.herramientas.CURRENT_ROUTE_KEY
import com.example.freetoplay_di.herramientas.Recursos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    //Repositorio que proporciona acceso a los datos
    private val repositorio: RepositorioBaseImpl,
    //Manejador de estado guardado
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //variable mutable para controlar la visibilidad del SplashScreen
    private val _splashScreenVisible = mutableStateOf(value = false)
    val splashScreenVisible: State<Boolean>
        get() = _splashScreenVisible

    //Flujo de estado para los juegos disponibles
    private val _juegos: MutableStateFlow<Recursos<List<Juego>>> =
        MutableStateFlow(value = Recursos.Loading())
    val juegos: StateFlow<Recursos<List<Juego>>>
        get() = _juegos

    //Flujo de estado para la ruta actual de navegación
    private val _currentRoute: MutableStateFlow<String> =
        MutableStateFlow(value = savedStateHandle.get(key = CURRENT_ROUTE_KEY) ?: "")
    val currentRoute: StateFlow<String>
        get() = _currentRoute

    //Inicialización del ViewModel
    init {
        //Lanzar una corrutina para cargar los juegos al iniciar el ViewModel
        viewModelScope.launch {
            _splashScreenVisible.value = true
            //Obtener la lista de juegos del repositorio
            _juegos.value = repositorio.obtenerTodosLosJuegos()
            _splashScreenVisible.value = false
        }
    }

    //Función para establecer la ruta actual de navegación
    fun setRoute(ruta: String) {
        _currentRoute.value = ruta
    }
}