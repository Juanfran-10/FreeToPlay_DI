package com.example.freetoplay_di.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.freetoplay_di.presentation.pantalla.base.Index
import com.example.freetoplay_di.presentation.pantalla.base.Pantalla
import com.example.freetoplay_di.presentation.theme.FreeToPlayTheme
import com.example.freetoplay_di.herramientas.ALL_GAMES_KEY
import com.example.freetoplay_di.herramientas.BROWSER_GAMES
import com.example.freetoplay_di.herramientas.FILTER_MODE_KEY
import com.example.freetoplay_di.herramientas.LATEST_GAMES
import com.example.freetoplay_di.herramientas.PC_GAMES
import com.example.freetoplay_di.herramientas.SEARCH_MODE_KEY
import com.example.freetoplay_di.herramientas.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //ViewModel de la actividad
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Configuración del SplashScreen
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.splashScreenVisible.value
            }
        }

        //Configuración del contenido de la actividad
        setContent {
            //Estado del Scaffold (estructura de diseño principal)
            val scaffoldState = rememberScaffoldState()

            //NavController para gestionar la navegación
            val navController = rememberNavController()

            //Estado de los juegos disponibles, recolectado como un estado del flujo
            val availableGames by mainViewModel.juegos
                .collectAsState()

            //Alcance de la Coroutine para manejar operaciones asíncronas
            val scope = rememberCoroutineScope()

            //Manejador de URI local
            val uriHandler = LocalUriHandler.current

            //Estado de la ruta actual, recolectado como un estado del flujo
            val route by mainViewModel.currentRoute
                .collectAsState()

            //Navegar a la ruta actual cuando cambia
            if (route.isNotEmpty()) {
                LaunchedEffect(key1 = route) {
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                }
            }

            //Tema de la aplicación
            FreeToPlayTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                //Componente principal de la aplicación (Index)
                Index(
                    scaffoldState = scaffoldState,
                    navController = navController,
                    availableGames = availableGames,
                    onOpenDrawer = {
                        //Abrir navegación
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    onSearchButtonClick = { juegos ->
                        //Navegar a la pantalla de búsqueda
                        val ruta = "search?mode=$SEARCH_MODE_KEY"
                        navController.navigate(
                            route = ruta,
                            onNavigate = {
                                //Establecer datos en el manejador de estados para la búsqueda
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = ALL_GAMES_KEY,
                                    value = juegos
                                )
                                //Establecer la ruta actual en el ViewModel
                                mainViewModel.setRoute(ruta = ruta)
                            }
                        )
                    },
                    onGameClick = { gameId ->
                        //Navegar a la pantalla de detalles del juego
                        val ruta = "gameDetail/$gameId"
                        navController.navigate(
                            route = ruta,
                            onNavigate = {
                                //Establecer la ruta actual en el ViewModel
                                mainViewModel.setRoute(ruta = ruta)
                            }
                        )
                    },
                    onPlayTheGameClicked = { urlJuego ->
                        //Abrir la URL del juego
                        uriHandler.openUri(uri = urlJuego)
                    },
                    onHomeMenuClick = {
                        //Navegar a la pantalla de inicio
                        scope.launch {
                            val ruta = Pantalla.HomePantalla.ruta
                            navController.navigate(
                                route = ruta,
                                onNavigate = {
                                    //Establecer la ruta actual en el ViewModel
                                    mainViewModel.setRoute(ruta = ruta)
                                    //Cerrar navegación
                                    scaffoldState.drawerState.close()
                                }
                            )
                        }
                    },
                    onPCGamesClick = {
                        //Navegar a la pantalla de juegos de PC
                        scope.launch {
                            val ruta = "search?mode=$FILTER_MODE_KEY&filter=$PC_GAMES"
                            navController.navigate(
                                route = ruta,
                                onNavigate = {
                                    //Establecer la ruta actual en el ViewModel
                                    mainViewModel.setRoute(ruta = ruta)
                                    // Cerrar navegación
                                    scaffoldState.drawerState.close()
                                }
                            )
                        }
                    },
                    onWebGamesClick = {
                        //Navegar a la pantalla de juegos de navegador web
                        scope.launch {
                            val ruta = "search?mode=$FILTER_MODE_KEY&filter=$BROWSER_GAMES"
                            navController.navigate(
                                route = ruta,
                                onNavigate = {
                                    //Establecer la ruta actual en el ViewModel
                                    mainViewModel.setRoute(ruta = ruta)
                                    // Cerrar navegación
                                    scaffoldState.drawerState.close()
                                }
                            )
                        }
                    },
                    onLatestGamesClick = {
                        //Navegar a la pantalla de los juegos más recientes
                        scope.launch {
                            val ruta = "search?mode=$FILTER_MODE_KEY&filter=$LATEST_GAMES"
                            navController.navigate(
                                route = ruta,
                                onNavigate = {
                                    //Establecer la ruta actual en el ViewModel
                                    mainViewModel.setRoute(ruta = ruta)
                                    //Cerrar navegación
                                    scaffoldState.drawerState.close()
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}


