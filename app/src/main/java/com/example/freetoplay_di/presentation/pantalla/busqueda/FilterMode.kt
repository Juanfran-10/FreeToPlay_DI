package com.example.freetoplay_di.presentation.pantalla.busqueda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.presentation.componentes.CarouselView
import com.example.freetoplay_di.presentation.componentes.SearchDetailCard
import com.example.freetoplay_di.presentation.componentes.TopBar
import com.example.freetoplay_di.herramientas.getUrls

@Composable
fun FilterMode(
    juegos: List<Juego>,
    isLoading: Boolean,
    onGameClick: (Int) -> Unit,
    onOpenDrawer: () -> Unit,
    onSearchClick: () -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 15.dp),
        modifier = Modifier.fillMaxSize()
    ){

        item{
            TopBar(
                onOpenDrawer = onOpenDrawer,
                onSearchButtonClick = onSearchClick
            )
        }

        item {
            CarouselView(
                modifier = Modifier
                    .requiredHeight(height = 260.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                urls = juegos.getUrls(),
                shape = MaterialTheme.shapes.medium,
                crossFade = 1000
            )
            Spacer(modifier = Modifier.height(30.dp ))
        }

        if(isLoading){
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
        } else{
            items(items = juegos){ game ->
                Box(modifier = Modifier.padding(horizontal = 8.dp)){
                    SearchDetailCard(
                        juego = game,
                        onClick = onGameClick
                    )
                }
            }
        }
    }

}