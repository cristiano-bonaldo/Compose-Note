package cvb.com.br.composenote.core.presentation.util

sealed class NavRoute(val route: String) {

    data object RouteNoteList: NavRoute("page_note_list")

    data object RouteNoteEdit: NavRoute("page_note_edit")

}
