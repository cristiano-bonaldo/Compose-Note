package cvb.com.br.composenote.feature_note.presentation.page_note_list

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cvb.com.br.composenote.R
import cvb.com.br.composenote.core.presentation.component.CompLoadingData
import cvb.com.br.composenote.core.presentation.component.CompOkCancelDialog
import cvb.com.br.composenote.core.presentation.component.CompTopBar
import cvb.com.br.composenote.core.presentation.util.Constant
import cvb.com.br.composenote.core.presentation.util.NavRoute
import cvb.com.br.composenote.core.presentation.util.handleError
import cvb.com.br.composenote.feature_note.presentation.page_note_list.component.CompNoteRow
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageNoteList(
    viewModel: PageNoteListViewModel = hiltViewModel(),
    navController: NavController
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(

        topBar = {
            CompTopBar(
                idTitle = R.string.app_name,
                navigationIcon = Icons.Rounded.Home,
                navigationIconClick = {},
            ) {
                IconButton(onClick = {
                    viewModel.handleEvent(EventPageNoteList.ChangeFilterOptionsVisibility)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "Localized description"
                    )
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { navController.navigate(route = NavRoute.RouteNoteEdit.route + "/" + Constant.NAV_PARAM_NEW_NOTE_ID) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = stringResource(id = R.string.add_note),
                    tint = Color.White
                )
            }
        },

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data -> Snackbar(snackbarData = data) }
        }

    ) { innerPadding ->
        PageContent(viewModel, navController, snackbarHostState, innerPadding)
    }
}

@Composable
fun PageContent(
    viewModel: PageNoteListViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = state.isFilterOptionsVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                FilterArea(viewModel = viewModel)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                items(state.listNote) { note ->
                    CompNoteRow(
                        note = note,
                        onClickEdit = {
                            navController.navigate(route = NavRoute.RouteNoteEdit.route + "/" + note.id)
                        },
                        onClickDelete = {
                            viewModel.handleEvent(EventPageNoteList.ShowDialogDelete(note))
                        })
                }
            }
        }

        if (state.isLoading) {
            CompLoadingData()
        }

        state.noteForDelete?.let { note ->
            val msg = stringResource(id = R.string.delete_message, note.title)

            CompOkCancelDialog(
                onDismissRequest = { },
                idOkTitle = R.string.bt_yes,
                idCancelTitle = R.string.bt_no,
                onOkEvent = {
                    viewModel.handleEvent(EventPageNoteList.ConfirmDelete)
                },
                onCancelEvent = {
                    viewModel.handleEvent(EventPageNoteList.CancelDelete)
                },
                dialogTitle = stringResource(id = R.string.delete),
                dialogText = msg,
                icon = Icons.Rounded.Warning
            )
        }

        state.stateError?.let { stateError ->
            val context: Context = LocalContext.current
            val msgError = stateError.handleError(context)

            CompOkCancelDialog(
                onDismissRequest = { },
                idOkTitle = R.string.bt_ok,
                onOkEvent = {
                    viewModel.handleEvent(EventPageNoteList.DismissDialogError)
                },
                dialogTitle = stringResource(id = R.string.error),
                dialogText = msgError,
                icon = Icons.Rounded.Warning
            )
        }

        if (state.showDeleteSnackBar) {
            val snackMessage = stringResource(id = R.string.snack_delete_message)
            val snackAction = stringResource(id = R.string.bt_undo)

            LaunchedEffect(snackbarHostState) {
                val result = snackbarHostState
                    .showSnackbar(
                        message = snackMessage,
                        actionLabel = snackAction,
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    )

                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        viewModel.handleEvent(EventPageNoteList.ExecuteDeleteRollback)
                    }

                    SnackbarResult.Dismissed -> {
                        viewModel.handleEvent(EventPageNoteList.DismissDeleteSnackBar)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterArea(viewModel: PageNoteListViewModel) {
    val state = viewModel.state.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.sort_type),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                EnumOrderType.values().forEach { type ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = (state.selectedOrderType == type),
                            onClick = {
                                viewModel.handleEvent(
                                    EventPageNoteList.UpdateOrderType(type)
                                )
                            }
                        )
                        Text(
                            text = stringResource(id = type.idString),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.sort_field),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                EnumOrderField.values().forEach { field ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = (state.selectedOrderField == field),
                            onClick = {
                                viewModel.handleEvent(
                                    EventPageNoteList.UpdateOrderField(field)
                                )
                            }
                        )
                        Text(
                            text = stringResource(id = field.idString),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
