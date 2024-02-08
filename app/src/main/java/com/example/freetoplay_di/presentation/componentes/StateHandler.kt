package com.example.freetoplay_di.presentation.componentes

import androidx.compose.runtime.Composable
import com.example.freetoplay_di.herramientas.Recursos

@Composable
fun <T> StateHandler(
    state: Recursos<T>,
    onLoading: @Composable (Recursos<T>) -> Unit,
    onFailure: @Composable (Recursos<T>) -> Unit,
    onSuccess: @Composable (Recursos<T>) -> Unit
){

    if(state is Recursos.Loading){
        onLoading(state)
    }
    if(state is Recursos.Error){
        onFailure(state)
    }
    onSuccess(state)

}