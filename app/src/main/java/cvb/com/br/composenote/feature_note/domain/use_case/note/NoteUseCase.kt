package cvb.com.br.composenote.feature_note.domain.use_case.note

import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import javax.inject.Inject

/*
data class NoteUseCase @Inject constructor(
    val getListNote: GetListNote,
    val getNoteById: GetNoteById,
    val saveNote: SaveNote,
    val deleteNote: DeleteNote
)
*/

class NoteUseCase @Inject constructor(
    private val getListNoteUseCase: GetListNote,
    private val getNoteByIdUseCase: GetNoteById,
    private val saveNoteUseCase: SaveNote,
    private val deleteNoteUseCase: DeleteNote
) {
    fun getListNote(orderType: EnumOrderType, orderField: EnumOrderField) =
        getListNoteUseCase(orderType, orderField)

    fun getNoteById(id: Long) = getNoteByIdUseCase(id)

    fun saveNote(note: Note) = saveNoteUseCase(note)

    fun deleteNote(note: Note) = deleteNoteUseCase(note)
}

