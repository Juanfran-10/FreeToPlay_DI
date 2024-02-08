package com.example.freetoplay_di.presentation.pantalla.juegos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.freetoplay_di.presentation.componentes.CarouselView
import com.example.freetoplay_di.presentation.componentes.ExpandableText
import com.example.freetoplay_di.presentation.componentes.ExtraInformationRow
import com.example.freetoplay_di.presentation.componentes.LoadingGameDetail
import com.example.freetoplay_di.presentation.componentes.NavBar
import com.example.freetoplay_di.presentation.componentes.NetworkImage
import com.example.freetoplay_di.presentation.componentes.StateHandler
import com.example.freetoplay_di.presentation.componentes.WarningMessage
import com.example.freetoplay_di.presentation.theme.RedErrorLight
import com.example.freetoplay_di.herramientas.LOADING
import com.example.freetoplay_di.R
import com.example.freetoplay_di.presentation.componentes.LeadingIconButton
import com.example.freetoplay_di.presentation.componentes.Platform
import com.example.freetoplay_di.presentation.theme.Gray400

@Composable
fun GameDetailScreen(
    viewModel: DetallesJuegoViewModel,
    navController: NavHostController,
    onPlayTheGameClicked: (String) -> Unit
) {

    val gameDetailState by viewModel.detallesJuegoState
        .collectAsState()

    val gameTitleState by viewModel.tituloJuego

    Column(modifier = Modifier.fillMaxSize()) {
        NavBar(
            title = stringResource(id = R.string.lbl_detail, gameTitleState),
            onBackPress = {
                navController.navigateUp()
            }
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        StateHandler(
            state = gameDetailState,
            onLoading = {
                LoadingGameDetail(
                    onLoading = { viewModel.setTituloJuego(titulo = LOADING) }
                )
            },
            onFailure = {
                WarningMessage(textId = R.string.txt_empty_result)
            }
        ) { resource ->
            resource.datos?.let { gameDetail ->

                val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
                        LocalDensity.current.density

                viewModel.setTituloJuego(titulo = gameDetail.titulo)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState())
                ) {

                    if (gameDetail.screenShots.isEmpty()) {
                        NetworkImage(
                            url = gameDetail.miniatura,
                            crossFade = 1000,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(height = screenHeight * 0.6f)
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(shape = MaterialTheme.shapes.medium),
                            onLoading = {
                                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                    val indicatorRef = createRef()
                                    CircularProgressIndicator(
                                        modifier = Modifier.constrainAs(indicatorRef) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                    )
                                }
                            },
                            onError = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warning),
                                    contentDescription = "",
                                    tint = RedErrorLight
                                )
                            }
                        )
                    } else {
                        CarouselView(
                            urls = gameDetail.screenShots.map { it.imagen },
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(height = screenHeight * 0.6f)
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .align(alignment = Alignment.CenterHorizontally),
                            shape = MaterialTheme.shapes.medium,
                            crossFade = 1000
                        )
                    }
                    Spacer(modifier = Modifier.height(height = 30.dp))
                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {

                        Text(
                            text = stringResource(id = R.string.lbl_about, gameDetail.titulo),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        ExpandableText(
                            text = gameDetail.descripcion,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(height = 30.dp))
                        Text(
                            text = stringResource(id = R.string.lbl_extra),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        ExtraInformationRow(
                            firstTitle = stringResource(id = R.string.lbl_title),
                            secondTitle = stringResource(id = R.string.lbl_developer),
                            textColor = MaterialTheme.colors.onSurface
                        )
                        ExtraInformationRow(
                            firstTitle = gameDetail.titulo,
                            secondTitle = gameDetail.desarrollador,
                            textColor = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                        ExtraInformationRow(
                            firstTitle = stringResource(id = R.string.lbl_publisher),
                            secondTitle = stringResource(id = R.string.lbl_release_date),
                            textColor = MaterialTheme.colors.onSurface
                        )
                        ExtraInformationRow(
                            firstTitle = gameDetail.editor,
                            secondTitle = gameDetail.fechaLanzamiento,
                            textColor = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                        ExtraInformationRow(
                            firstTitle = stringResource(id = R.string.lbl_genre),
                            secondTitle = stringResource(id = R.string.lbl_platform),
                            textColor = MaterialTheme.colors.onSurface
                        )
                        ExtraInformationRow(
                            firstTitle = gameDetail.genero,
                            secondTitle = gameDetail.plataforma,
                            textColor = MaterialTheme.colors.onBackground,
                            icon = {
                                Box(modifier = Modifier.padding(end = 5.dp)) {
                                    Platform(text = gameDetail.plataforma)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(height = 30.dp))
                        gameDetail.requisitosMinimosSistema?.let { minimumSystemRequirements ->
                            Text(
                                text = stringResource(id = R.string.lbl_msr),
                                style = MaterialTheme.typography.h2,
                                color = MaterialTheme.colors.onSurface
                            )
                            Spacer(modifier = Modifier.height(height = 20.dp))
                            Text(
                                text = stringResource(id = R.string.lbl_os),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = minimumSystemRequirements.sistemaOperativo,
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onBackground
                            )
                            Spacer(modifier = Modifier.height(height = 15.dp))
                            Text(
                                text = stringResource(id = R.string.lbl_memory),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = minimumSystemRequirements.memoria,
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onBackground
                            )
                            Spacer(modifier = Modifier.height(height = 15.dp))
                            Text(
                                text = stringResource(id = R.string.lbl_storage),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = minimumSystemRequirements.almacenamiento,
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onBackground
                            )
                            Spacer(modifier = Modifier.height(height = 15.dp))
                            Text(
                                text = stringResource(id = R.string.lbl_graphics),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = minimumSystemRequirements.graficos,
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onBackground
                            )
                            Spacer(modifier = Modifier.height(height = 15.dp))
                            Text(
                                text = stringResource(
                                    id = R.string.lbl_developer_copyright,
                                    gameDetail.desarrollador
                                ),
                                fontSize = 11.sp,
                                color = Gray400
                            )
                            Spacer(modifier = Modifier.height(height = 30.dp))
                        }
                        LeadingIconButton(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            iconResId = R.drawable.ic_sign_in_alt_solid,
                            textButton = stringResource(id = R.string.lbl_play_the_game),
                            onClick = {
                                onPlayTheGameClicked(gameDetail.urlJuego)
                            }
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                    }
                }
            }
        }
    }
}