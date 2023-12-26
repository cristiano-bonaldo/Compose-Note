package cvb.com.br.composenote.feature_note.presentation.page_note_list

import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType

sealed class EventPageNoteList {

    data object DismissDialogError: EventPageNoteList()

    data class ShowDialogDelete(val note: Note): EventPageNoteList()

    data object CancelDelete: EventPageNoteList()

    data object ConfirmDelete: EventPageNoteList()

    data object DismissDeleteSnackBar: EventPageNoteList()

    data object ExecuteDeleteRollback: EventPageNoteList()

    data class UpdateOrderType(val type: EnumOrderType): EventPageNoteList()

    data class UpdateOrderField(val field: EnumOrderField): EventPageNoteList()

    data object ChangeFilterOptionsVisibility: EventPageNoteList()

}
