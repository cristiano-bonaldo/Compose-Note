package cvb.com.br.composenote.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cvb.com.br.composenote.core.presentation.util.Constant
import cvb.com.br.composenote.core.presentation.util.NavRoute
import cvb.com.br.composenote.feature_note.presentation.page_note_edit.PageNoteEdit
import cvb.com.br.composenote.feature_note.presentation.page_note_list.PageNoteList

@Composable
fun CompNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.RouteNoteList.route) {

        composable(route = NavRoute.RouteNoteList.route) {
            PageNoteList(navController = navController)
        }

        composable(route = NavRoute.RouteNoteEdit.route + "/{${Constant.NAV_PARAM_NOTE_ID}}") {
            PageNoteEdit(navController = navController)
        }
    }
}