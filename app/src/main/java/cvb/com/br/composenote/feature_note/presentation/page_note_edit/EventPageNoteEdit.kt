package cvb.com.br.composenote.feature_note.presentation.page_note_edit

import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor

sealed class EventPageNoteEdit {

    data class SetSelectedColor(val enumColor: EnumColor) : EventPageNoteEdit()

    data class UpdateTextForTitle(val text: String) : EventPageNoteEdit()

    data class UpdateTextForInfo(val text: String) : EventPageNoteEdit()

    data object DismissError : EventPageNoteEdit()

    data object SaveNote : EventPageNoteEdit()
}
