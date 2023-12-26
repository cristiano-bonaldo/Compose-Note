package cvb.com.br.composenote.feature_note.data.repository

import cvb.com.br.composenote.feature_note.domain.data_source.NoteDS
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val datasource: NoteDS) : NoteRepository {

    override fun getListNote(orderType: EnumOrderType, orderField: EnumOrderField): Flow<List<Note>> {
        return datasource.getListNote().map { list ->
            when (orderType) {
                EnumOrderType.ASCENDING -> {
                        when (orderField) {
                            EnumOrderField.DATE -> list.sortedBy { note -> note.timestamp }
                            EnumOrderField.TITLE -> list.sortedBy { note -> note.title }
                            EnumOrderField.COLOR -> list.sortedBy { note -> note.color }
                        }
                    }
                EnumOrderType.DESCENDING -> {
                        when (orderField) {
                            EnumOrderField.DATE -> list.sortedByDescending { note -> note.timestamp }
                            EnumOrderField.TITLE -> list.sortedByDescending { note -> note.title }
                            EnumOrderField.COLOR -> list.sortedByDescending { note -> note.color }
                        }
                }
            }
        }
    }

    override suspend fun getNoteById(id: Long): Note? =
        datasource.getNoteById(id)

    override suspend fun deleteNote(note: Note) =
        datasource.deleteNote(note)

    override suspend fun saveNote(note: Note) =
        datasource.saveNote(note)
}