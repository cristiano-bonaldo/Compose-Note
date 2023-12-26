package cvb.com.br.composenote.feature_note.presentation.page_note_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvb.com.br.composenote.R
import cvb.com.br.composenote.core.presentation.util.Constant
import cvb.com.br.composenote.core.presentation.util.StateError
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.use_case.note.NoteUseCase
import cvb.com.br.composenote.feature_note.domain.util.Resource
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PageNoteEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    private val mState = mutableStateOf(StatePageEditNote())
    val state: State<StatePageEditNote> = mState

    private var idNote: String = Constant.NAV_PARAM_NEW_NOTE_ID.toString()

    init {
        idNote = savedStateHandle.get<String>(Constant.NAV_PARAM_NOTE_ID)
            ?: Constant.NAV_PARAM_NEW_NOTE_ID.toString()

        loadNote(idNote)
    }

    private fun loadNote(idNote: String) {
        try {
            val id = idNote.toLong()

            if (id > Constant.NAV_PARAM_NEW_NOTE_ID) {
                findNote(id)
            }
        } catch (e: Exception) {
            mState.value = mState.value.copy(stateError = StateError())
        }
    }

    private fun findNote(idNote: Long) {
        noteUseCase.getNoteById(idNote).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    mState.value = mState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    resource.data?.let { note ->

                        val enumColor =
                            EnumColor.values().first { enumColor -> enumColor.id == note.color }

                        mState.value = mState.value.copy(
                            isLoading = false,
                            selectedColor = enumColor,
                            textTitle = note.title,
                            textInfo = note.info
                        )
                    } ?: run {
                        val stateError = StateError(msgId = R.string.error_find_note)
                        mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                    }
                }

                is Resource.Error -> {
                    val stateError = StateError(msgId = resource.msgId, msgInfo = resource.msgInfo)
                    mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addNote(note: Note) {
        noteUseCase.saveNote(note).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    mState.value = mState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    // Do nothing - Just remove loading
                    mState.value = mState.value.copy(isLoading = false, isNoteSaved = true)
                }

                is Resource.Error -> {
                    val stateError = StateError(msgId = resource.msgId, msgInfo = resource.msgInfo)
                    mState.value = mState.value.copy(isLoading = false, stateError = stateError)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun handleEvent(event: EventPageNoteEdit) {
        when (event) {
            is EventPageNoteEdit.SetSelectedColor -> {
                setSelectedColor(event.enumColor)
            }

            is EventPageNoteEdit.UpdateTextForTitle -> {
                updateTextForTitle(event.text)
            }

            is EventPageNoteEdit.UpdateTextForInfo -> {
                updateTextForInfo(event.text)
            }

            is EventPageNoteEdit.DismissError -> {
                dismissError()
            }

            is EventPageNoteEdit.SaveNote -> {
                val id: Long? = if (idNote.toLong() == Constant.NAV_PARAM_NEW_NOTE_ID.toLong()) {
                    null
                } else {
                    idNote.toLong()
                }

                val note = Note(
                    id= id,
                    title = mState.value.textTitle,
                    info = mState.value.textInfo,
                    timestamp = System.currentTimeMillis(),
                    color = mState.value.selectedColor.id
                )
                addNote(note)
            }
        }
    }

    private fun setSelectedColor(enumColor: EnumColor) {
        mState.value = mState.value.copy(selectedColor = enumColor)
    }

    private fun updateTextForTitle(text: String) {
        mState.value = mState.value.copy(textTitle = text)
    }

    private fun updateTextForInfo(text: String) {
        mState.value = mState.value.copy(textInfo = text)
    }

    private fun dismissError() {
        mState.value = mState.value.copy(stateError = null)
    }
}