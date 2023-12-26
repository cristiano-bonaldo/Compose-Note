package cvb.com.br.composenote.feature_note.domain.use_case.note

import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNote @Inject constructor(private val repository: NoteRepository) {

    operator fun invoke(note: Note): Flow<Resource<Note>> {
        return flow {
            emit(Resource.Loading())
            try {
                repository.deleteNote(note)
                emit(Resource.Success(note))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        msgId = R.string.error_delete_note,
                        msgInfo = e.localizedMessage ?: "-"
                    )
                )
            }
        }
    }
}