package cvb.com.br.composenote.feature_note.presentation.page_note_edit

import cvb.com.br.composenote.core.presentation.util.StateError
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType

data class StatePageEditNote(
    val selectedColor: EnumColor = EnumColor.COLOR_4,
    val textTitle: String = "",
    val textInfo: String = "",
    val isLoading: Boolean = false,
    val stateError: StateError? = null,
    val isNoteSaved: Boolean = false,
)
