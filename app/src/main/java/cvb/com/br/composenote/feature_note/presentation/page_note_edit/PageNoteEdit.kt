package cvb.com.br.composenote.feature_note.presentation.page_note_edit

import android.content.Context
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cvb.com.br.composenote.R
import cvb.com.br.composenote.core.presentation.component.CompLoadingData
import cvb.com.br.composenote.core.presentation.component.CompOkCancelDialog
import cvb.com.br.composenote.core.presentation.component.CompTopBar
import cvb.com.br.composenote.core.presentation.util.handleError
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageNoteEdit(
    viewModel: PageNoteEditViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            CompTopBar(
                idTitle = R.string.page_edit,
                navigationIcon = Icons.Rounded.ArrowBack,
                navigationIconClick = { navController.popBackStack() },
                actions = {}
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.White,
                onClick = { viewModel.handleEvent(EventPageNoteEdit.SaveNote) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.save_note),
                    tint = colorResource(id = state.selectedColor.idColorResource)
                )
            }
        }

    ) { innerPadding ->
        PageContent(
            viewModel,
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageContent(
    viewModel: PageNoteEditViewModel,
    navController: NavController,
    modifier: Modifier
) {
    val state = viewModel.state.value

    val color = colorResource(id = state.selectedColor.idColorResource)

    val backgroundColor = remember(color) {
        Animatable(color)
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .padding(5.dp)
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(10.dp)

            )
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                EnumColor.entries.forEach { enumColor ->

                    val bgColor = colorResource(id = enumColor.idColorResource)

                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, shape = CircleShape)
                        .clip(shape = CircleShape)
                        .background(bgColor)
                        .border(
                            border = BorderStroke(
                                color = (if (enumColor == state.selectedColor) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                }),
                                width = 3.dp,
                            ),
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                backgroundColor.animateTo(
                                    targetValue = bgColor,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }

                            viewModel.handleEvent(
                                EventPageNoteEdit.SetSelectedColor(
                                    enumColor
                                )
                            )
                        })
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val textColor = if (state.selectedColor == EnumColor.COLOR_1) {
                Color.Black
            } else {
                Color.White
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.textTitle,
                maxLines = 3,
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = textColor
                ),
                onValueChange = { text ->
                    viewModel.handleEvent(
                        EventPageNoteEdit.UpdateTextForTitle(text)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.hint_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.textInfo,
                onValueChange = { text ->
                    viewModel.handleEvent(
                        EventPageNoteEdit.UpdateTextForInfo(text)
                    )
                },
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,//color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = textColor
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.hint_info),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        }

        if (state.isLoading) {
            CompLoadingData()
        }

        state.stateError?.let { stateError ->
            val context: Context = LocalContext.current
            val msgError = stateError.handleError(context)

            CompOkCancelDialog(
                onDismissRequest = { },
                idOkTitle = R.string.bt_ok,
                onOkEvent = {
                    viewModel.handleEvent(EventPageNoteEdit.DismissError)
                },
                dialogTitle = stringResource(id = R.string.error),
                dialogText = msgError,
                icon = Icons.Rounded.Warning
            )
        }

        if (state.isNoteSaved) {
            LaunchedEffect(true) {
                navController.popBackStack()
            }
        }
    }
}