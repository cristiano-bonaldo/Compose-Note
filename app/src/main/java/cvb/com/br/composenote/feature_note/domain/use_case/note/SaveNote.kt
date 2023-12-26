package cvb.com.br.composenote.feature_note.domain.use_case.note

import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveNote @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(note: Note): Flow<Resource<Note>> {
        return flow{

            val idError = validateNote(note)
            idError?.let { id ->
                emit(Resource.Error(msgId = id, msgInfo = ""))
                return@flow
            }

            emit(Resource.Loading())
            try {
                repository.saveNote(note)
                emit(Resource.Success(note))
            } catch (e: Exception) {
                emit(Resource.Error(msgId = R.string.error_save_note, msgInfo = e.localizedMessage ?: "-"))
            }
        }
    }

    private fun validateNote(note: Note): Int?  {
        if (note.title.isBlank()) {
            return R.string.error_invalid_title
        }
        if (note.info.isBlank()) {
            return R.string.error_invalid_info
        }
        return null
    }
}