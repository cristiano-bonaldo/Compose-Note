package cvb.com.br.composenote.feature_note.domain.use_case.note

import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteById @Inject constructor(private val repository: NoteRepository) {

    operator fun invoke(id: Long): Flow<Resource<Note?>> {
        return flow {
            emit(Resource.Loading())
            try {
                val note = repository.getNoteById(id)
                emit(Resource.Success(note))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        msgId = R.string.error_recover_note,
                        msgInfo = e.localizedMessage ?: "-"
                    )
                )
            }
        }
    }
}