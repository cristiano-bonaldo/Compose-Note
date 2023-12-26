package cvb.com.br.composenote.feature_note.presentation.page_note_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvb.com.br.composenote.core.presentation.util.StateError
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.use_case.note.NoteUseCase
import cvb.com.br.composenote.feature_note.domain.util.Resource
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageNoteListViewModel @Inject constructor(
    private val useCase: NoteUseCase
) : ViewModel() {

    private val mState = mutableStateOf(StatePageListNote())
    val state: State<StatePageListNote> = mState

    init {
        loadNotes()
    }

    private var jobLoadNotes: Job? = null

    private var lastNoteDeleted: Note? = null

    private fun loadNotes() {
        jobLoadNotes?.cancel()

        jobLoadNotes = useCase.getListNote(
            state.value.selectedOrderType,
            state.value.selectedOrderField
        ).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    mState.value = mState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    resource.data?.onEach { listNote ->
                        mState.value = mState.value.copy(isLoading = false, listNote = listNote)
                    }?.launchIn(viewModelScope) ?: run {
                        mState.value = mState.value.copy(isLoading = false)
                    }
                }

                is Resource.Error -> {
                    val stateError = StateError(msgId = resource.msgId, msgInfo = resource.msgInfo)
                    mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteNote(note: Note) {
        useCase.deleteNote(note).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    mState.value = mState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    // Do nothing - Just remove loading
                    mState.value = mState.value.copy(isLoading = false)
                }

                is Resource.Error -> {
                    val stateError = StateError(msgId = resource.msgId, msgInfo = resource.msgInfo)
                    mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addNote(note: Note) {
        useCase.saveNote(note).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    mState.value = mState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    // Do nothing - Just remove loading
                    mState.value = mState.value.copy(isLoading = false)
                }

                is Resource.Error -> {
                    val stateError = StateError(msgId = resource.msgId, msgInfo = resource.msgInfo)
                    mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateOrderType(type: EnumOrderType) {
        mState.value = state.value.copy(selectedOrderType = type)
    }

    private fun updateOrderField(field: EnumOrderField) {
        mState.value = state.value.copy(selectedOrderField = field)
    }

    private fun dismissDialogForDelete() {
        mState.value = state.value.copy(noteForDelete = null)
    }

    private fun changeFilterOptionsVisibility() {
        mState.value =
            state.value.copy(isFilterOptionsVisible = !mState.value.isFilterOptionsVisible)
    }

    private fun setNoteForDelete(note: Note) {
        mState.value = state.value.copy(noteForDelete = note)
    }

    private fun dismissDialogError() {
        mState.value = state.value.copy(stateError = null)
    }

    private fun showDeleteSnackBar() {
        mState.value = state.value.copy(showDeleteSnackBar = true)
    }

    private fun dismissDeleteSnackBar() {
        lastNoteDeleted = null

        mState.value = state.value.copy(showDeleteSnackBar = false)
    }

    fun handleEvent(event: EventPageNoteList) {
        when (event) {
            is EventPageNoteList.DismissDialogError -> {
                dismissDialogError()
            }

            is EventPageNoteList.ShowDialogDelete -> {
                setNoteForDelete(event.note)
            }

            is EventPageNoteList.ConfirmDelete -> {
                mState.value.noteForDelete?.let { note ->
                    deleteNote(note)
                    lastNoteDeleted = note
                    dismissDialogForDelete()
                    showDeleteSnackBar()
                } ?: run {
                    dismissDialogForDelete()
                }
            }

            is EventPageNoteList.CancelDelete -> {
                dismissDialogForDelete()
            }

            is EventPageNoteList.UpdateOrderType -> {
                if (mState.value.selectedOrderType == event.type) {
                    return
                }
                updateOrderType(event.type)
                loadNotes()
            }

            is EventPageNoteList.UpdateOrderField -> {
                if (mState.value.selectedOrderField == event.field) {
                    return
                }
                updateOrderField(event.field)
                loadNotes()
            }

            is EventPageNoteList.ChangeFilterOptionsVisibility -> {
                changeFilterOptionsVisibility()
            }

            is EventPageNoteList.ExecuteDeleteRollback -> {
                lastNoteDeleted?.let { note ->
                    val newNote: Note = note.copy(id = null)
                    addNote(newNote)
                    dismissDeleteSnackBar()
                }
            }

            is EventPageNoteList.DismissDeleteSnackBar -> {
                dismissDeleteSnackBar()
            }
        }
    }
}