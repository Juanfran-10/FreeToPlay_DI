package com.example.freetoplay_di.presentation.pantalla.busqueda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.herramientas.ALL_GAMES_KEY
import com.example.freetoplay_di.herramientas.SEARCH_MODE_KEY
import com.example.freetoplay_di.herramientas.navigate
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    juegos: List<Juego>
) {

    val screenMode by viewModel.modoPantalla
        .collectAsState()

    val isLoading by viewModel.isLoading
        .collectAsState(initial = false)

    val searchDetailVisible by viewModel.searchDetailVisible
        .collectAsState()

    val availableGames by viewModel.juegos
        .collectAsState()

    val searchQuery by viewModel.query
        .collectAsState()

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        if(screenMode == SEARCH_MODE_KEY){
            SearchMode(
                isLoading = isLoading,
                focusManager = focusManager,
                searchDetailVisible = searchDetailVisible,
                searchSuggestions = availableGames,
                query = searchQuery,
                onClearQuery = {
                    viewModel.limpiarConsultaBusqueda()
                    navController.popBackStack()
                },
                onSearch = { query ->
                    viewModel.onQuery(query = query)
                    if(query.isNotEmpty()){
                        viewModel.busqueda(juegos = juegos)
                    }
                },
                search = {
                    if(searchQuery.isNotEmpty()){
                        viewModel.mostrarBusquedaDetallada()
                    }
                },
                onItemClick = { id ->
                    val route = "gameDetail/$id"
                    navController.navigate(
                        route = route,
                        onNavigate = {
                            viewModel.setRuta(ruta = route)
                        }
                    )
                }
            )
        } else{
            FilterMode(
                isLoading = isLoading,
                juegos = availableGames,
                onGameClick = { id ->
                    val route = "gameDetail/$id"
                    navController.navigate(
                        route = route,
                        onNavigate = {
                            viewModel.setRuta(ruta = route)
                        }
                    )
                },
                onOpenDrawer = {
                     scope.launch {
                         scaffoldState.drawerState.open()
                     }
                },
                onSearchClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = ALL_GAMES_KEY,
                        value = availableGames
                    )
                    val route = "search?mode=$SEARCH_MODE_KEY"
                    navController.navigate(
                        route = route,
                        onNavigate = {
                            viewModel.setRuta(ruta = route)
                        }
                    )
                }
            )
        }
    }
}