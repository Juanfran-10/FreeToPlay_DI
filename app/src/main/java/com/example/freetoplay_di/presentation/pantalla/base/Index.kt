package com.example.freetoplay_di.presentation.pantalla.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.presentation.componentes.drawer.NavigationDrawer
import com.example.freetoplay_di.presentation.componentes.drawer.NavigationDrawerItem
import com.example.freetoplay_di.presentation.pantalla.juegos.GameDetailScreen
import com.example.freetoplay_di.presentation.pantalla.juegos.DetallesJuegoViewModel
import com.example.freetoplay_di.presentation.pantalla.home.HomeScreen
import com.example.freetoplay_di.presentation.pantalla.busqueda.SearchScreen
import com.example.freetoplay_di.presentation.pantalla.busqueda.SearchViewModel
import com.example.freetoplay_di.herramientas.ALL_GAMES_KEY
import com.example.freetoplay_di.herramientas.Recursos
import com.example.freetoplay_di.herramientas.SEARCH_SCREEN_FILTER_KEY
import com.intuit.sdp.R

@Composable
fun Index(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    availableGames: Recursos<List<Juego>>,
    onOpenDrawer: () -> Unit,
    onSearchButtonClick: (List<Juego>) -> Unit,
    onGameClick: (Int) -> Unit,
    onPlayTheGameClicked: (String) -> Unit,
    onHomeMenuClick: () -> Unit,
    onPCGamesClick: () -> Unit,
    onWebGamesClick: () -> Unit,
    onLatestGamesClick: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        drawerContent = {
            NavigationDrawer(
                header = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(height = dimensionResource(id = R.dimen._200sdp))
                    ) {
                        Image(
                            modifier = Modifier
                                .size(150.dp)
                                .align(alignment = Alignment.Center),
                            painter = painterResource(id = com.example.freetoplay_di.R.drawable.ic_free_to_play_launcher),
                            contentDescription = "",
                        )
                    }
                },
                content = {
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = com.example.freetoplay_di.R.drawable.ic_bars_staggered_solid),
                        iconColor = MaterialTheme.colors.primary,
                        text = stringResource(id = com.example.freetoplay_di.R.string.lbl_home),
                        textStyle = MaterialTheme.typography.subtitle1,
                        textColor = MaterialTheme.colors.onBackground,
                        onClick = onHomeMenuClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = com.example.freetoplay_di.R.drawable.ic_windows_brands),
                        iconColor = MaterialTheme.colors.primary,
                        text = stringResource(id = com.example.freetoplay_di.R.string.lbl_pc_games),
                        textStyle = MaterialTheme.typography.subtitle1,
                        textColor = MaterialTheme.colors.onBackground,
                        onClick = onPCGamesClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = com.example.freetoplay_di.R.drawable.ic_window_maximize_solid),
                        iconColor = MaterialTheme.colors.primary,
                        text = stringResource(id = com.example.freetoplay_di.R.string.lbl_web_games),
                        textStyle = MaterialTheme.typography.subtitle1,
                        textColor = MaterialTheme.colors.onBackground,
                        onClick = onWebGamesClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = com.example.freetoplay_di.R.drawable.ic_arrow_trend_up_solid),
                        iconColor = MaterialTheme.colors.primary,
                        text = stringResource(id = com.example.freetoplay_di.R.string.lbl_latest_games),
                        textStyle = MaterialTheme.typography.subtitle1,
                        textColor = MaterialTheme.colors.onBackground,
                        onClick = onLatestGamesClick
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Pantalla.HomePantalla.ruta,
        ) {
            composable(route = Pantalla.HomePantalla.ruta) {
                HomeScreen(
                    onOpenDrawer = { onOpenDrawer() },
                    onSearchButtonClick = { onSearchButtonClick(it) },
                    onGameClick = { gameId ->
                        onGameClick(gameId)
                    },
                    availableGames = availableGames
                )
            }
            composable(route = Pantalla.DetallesJuegoPantalla.ruta) {
                val detallesJuegoViewModel = hiltViewModel<DetallesJuegoViewModel>()
                GameDetailScreen(
                    viewModel = detallesJuegoViewModel,
                    navController = navController,
                    onPlayTheGameClicked = { gameUrl ->
                        onPlayTheGameClicked(gameUrl)
                    }
                )
            }
            composable(
                route = Pantalla.SearchPantalla.ruta,
                arguments = listOf(
                    navArgument(name = SEARCH_SCREEN_FILTER_KEY) {
                        defaultValue = ""
                        type = NavType.StringType
                    }
                )
            ) {
                val searchViewModel = hiltViewModel<SearchViewModel>()
                val juegos =
                    navController.previousBackStackEntry?.savedStateHandle?.get<List<Juego>>(key = ALL_GAMES_KEY)
                        ?: emptyList()
                SearchScreen(
                    viewModel = searchViewModel,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    juegos = juegos,
                )
            }
        }
    }
}