package cvb.com.br.composenote.feature_note.domain.data_source

import cvb.com.br.composenote.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDS {
    fun getListNote(): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun deleteNote(note: Note)

    suspend fun saveNote(note: Note)
}