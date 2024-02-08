package com.example.freetoplay_di.presentation.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingGameDetail(onLoading: () -> Unit){
    onLoading()
    Box(
       modifier = Modifier.fillMaxSize(),
       contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }

}