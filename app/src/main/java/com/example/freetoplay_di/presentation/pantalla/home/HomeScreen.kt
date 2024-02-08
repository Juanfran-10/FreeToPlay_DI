package com.example.freetoplay_di.presentation.pantalla.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.freetoplay_di.R
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.presentation.componentes.CarouselView
import com.example.freetoplay_di.presentation.componentes.GameCard
import com.example.freetoplay_di.presentation.componentes.TopBar
import com.example.freetoplay_di.presentation.componentes.WarningMessage
import com.example.freetoplay_di.herramientas.Recursos
import com.example.freetoplay_di.herramientas.getUrls
import com.example.freetoplay_di.herramientas.header

@Composable
fun HomeScreen(
    onOpenDrawer: () -> Unit,
    onSearchButtonClick: (List<Juego>) -> Unit,
    onGameClick: (Int) -> Unit,
    availableGames: Recursos<List<Juego>>
) {
   availableGames.datos?.let { games ->

       val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
               LocalDensity.current.density

       if(games.isEmpty()){
            WarningMessage(textId = R.string.wrn_no_games)
       }
       else{
           Column(
               modifier = Modifier.fillMaxSize()
           ) {
               TopBar(
                   onOpenDrawer = onOpenDrawer,
                   onSearchButtonClick = { onSearchButtonClick(games) }
               )

               LazyVerticalGrid(columns = GridCells.Fixed(count = 2)){
                   header{
                       CarouselView(
                           modifier = Modifier
                               .requiredHeight(height = 260.dp)
                               .fillMaxWidth()
                               .padding(vertical = 8.dp, horizontal = 12.dp),
                           urls = games.getUrls(),
                           shape = MaterialTheme.shapes.medium,
                           crossFade = 1000
                       )
                       Spacer(modifier = Modifier.height(30.dp ))
                   }
                   items(items = games){ game ->
                       GameCard(
                           modifier = Modifier
                               .padding(all = 8.dp)
                               .requiredHeight(height = screenHeight * 0.45f),
                           juego = game,
                           onClick = { onGameClick(game.id) }
                       )
                   }
               }
           }
       }
   }

}