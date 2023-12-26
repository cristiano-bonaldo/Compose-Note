package cvb.com.br.composenote.feature_note.data.data_source.local

import cvb.com.br.composenote.feature_note.data.db.dao.NoteDao
import cvb.com.br.composenote.feature_note.data.mapper.toNote
import cvb.com.br.composenote.feature_note.data.mapper.toNoteEntity
import cvb.com.br.composenote.feature_note.domain.data_source.NoteDS
import cvb.com.br.composenote.feature_note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class NoteLocalDS @Inject constructor(private val dao: NoteDao): NoteDS {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getListNote(): Flow<List<Note>> {
        val list = dao.getListNoteEntity()

        val flowOfNote = list.flatMapConcat { listNoteEntity ->
            val listNote = listNoteEntity.map { noteEntity -> noteEntity.toNote() }
            flowOf(listNote)
        }

        return flowOfNote
    }

    override suspend fun getNoteById(id: Long): Note? {
        val noteEntity = dao.getNoteEntityById(id)
        return noteEntity.toNote()
    }

    override suspend fun deleteNote(note: Note) {
        val noteEntity = note.toNoteEntity()
        dao.deleteNoteEntity(noteEntity)
    }

    override suspend fun saveNote(note: Note) {
        val noteEntity = note.toNoteEntity()
        dao.saveNoteEntity(noteEntity)
    }
}