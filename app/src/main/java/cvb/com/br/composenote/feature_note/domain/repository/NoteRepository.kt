package cvb.com.br.composenote.feature_note.domain.repository

import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getListNote(orderType: EnumOrderType, orderField: EnumOrderField): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun deleteNote(note: Note)

    suspend fun saveNote(note: Note)

}