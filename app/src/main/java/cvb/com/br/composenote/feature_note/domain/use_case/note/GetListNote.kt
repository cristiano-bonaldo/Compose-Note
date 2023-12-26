package cvb.com.br.composenote.feature_note.domain.use_case.note

import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListNote @Inject constructor(private val repository: NoteRepository) {

    operator fun invoke(orderType: EnumOrderType, orderField: EnumOrderField): Flow<Resource<Flow<List<Note>>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val flow = repository.getListNote(orderType, orderField)
                emit(Resource.Success(flow))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        msgId = R.string.error_recover_list_note,
                        msgInfo = e.localizedMessage ?: "-"
                    )
                )
            }
        }
    }
}