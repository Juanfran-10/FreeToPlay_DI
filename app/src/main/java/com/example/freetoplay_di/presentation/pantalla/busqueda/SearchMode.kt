package com.example.freetoplay_di.presentation.pantalla.busqueda

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.freetoplay_di.dominio.modelos.Juego
import com.example.freetoplay_di.presentation.componentes.SearchBar
import com.example.freetoplay_di.presentation.componentes.SearchDetail
import com.example.freetoplay_di.presentation.componentes.SearchSuggestions

@Composable
fun SearchMode(
    isLoading: Boolean,
    searchSuggestions: List<Juego>,
    focusManager: FocusManager,
    onItemClick: (Int) -> Unit,
    onClearQuery: () -> Unit,
    onSearch: (String) -> Unit,
    search: () -> Unit,
    query: String,
    searchDetailVisible: Boolean
){

    SearchBar(
        query = query,
        focusManager = focusManager,
        onClearQuery = onClearQuery,
        onSearch = onSearch,
        search = search
    )

    if(isLoading){
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    } else{
       Spacer(modifier = Modifier.padding(vertical = 16.dp))
       if(!searchDetailVisible){
           SearchSuggestions(
               query = query,
               searchResult = searchSuggestions,
               onClick =  { onItemClick(it) }
           )
       } else{
           SearchDetail(
               query = query,
               searchResult = searchSuggestions,
               onClick = { onItemClick(it) }
           )
       }

    }


}
