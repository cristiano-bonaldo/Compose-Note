package cvb.com.br.composenote.feature_note.presentation.page_note_list

import cvb.com.br.composenote.core.presentation.util.StateError
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType

data class StatePageListNote(
    val isLoading: Boolean = false,
    val listNote: List<Note> = emptyList(),
    val stateError: StateError? = null,
    val isFilterOptionsVisible: Boolean = false,
    val selectedOrderType: EnumOrderType = EnumOrderType.ASCENDING,
    val selectedOrderField: EnumOrderField = EnumOrderField.DATE,
    val noteForDelete: Note? = null,
    val showDeleteSnackBar: Boolean = false
)