package cvb.com.br.composenote.feature_note.domain.use_case.note

import com.google.common.truth.Truth
import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import cvb.com.br.composenote.feature_note.util.MockitoUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetListNoteTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    @InjectMocks
    lateinit var useCase: GetListNote

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getListNote_Success() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)

        Mockito.`when`(
            noteRepository.getListNote(
                MockitoUtil.anyMockitoInstanceOf(EnumOrderType::class.java),
                MockitoUtil.anyMockitoInstanceOf(EnumOrderField::class.java)
            )
        ).thenReturn(flow)

        val listResult = useCase.invoke(EnumOrderType.ASCENDING, EnumOrderField.COLOR).toList()

        Mockito.verify(noteRepository, Mockito.times(1))
            .getListNote(EnumOrderType.ASCENDING, EnumOrderField.COLOR)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Success::class.java)

        Truth.assertThat(listResult[1].data).isNotNull()
        val returnedFlow = listResult[1].data!!
        val returnedList = returnedFlow.first()
        Truth.assertThat(returnedList).isNotNull()
        Truth.assertThat(returnedList).isEqualTo(listNote)
    }

    private fun getListNote() = listOf(
        Note(id = 1, title = "Title A", info = "Info A", timestamp = 2000, color = EnumColor.COLOR_1.id),
        Note(id = 3, title = "Title E", info = "Info E", timestamp = 3000, color = EnumColor.COLOR_4.id),
        Note(id = 5, title = "Title D", info = "Info D", timestamp = 1000, color = EnumColor.COLOR_3.id),
        Note(id = 4, title = "Title C", info = "Info C", timestamp = 5000, color = EnumColor.COLOR_5.id),
        Note(id = 2, title = "Title B", info = "Info B", timestamp = 4000, color = EnumColor.COLOR_2.id),
    )

    @Test
    fun getListNote_Error() = runTest {
        Mockito.doAnswer { throw Exception() }.`when`(noteRepository).getListNote(
            MockitoUtil.anyMockitoInstanceOf(EnumOrderType::class.java),
            MockitoUtil.anyMockitoInstanceOf(EnumOrderField::class.java)
        )

        val listResult = useCase.invoke(EnumOrderType.ASCENDING, EnumOrderField.COLOR).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).getListNote(
            EnumOrderType.ASCENDING, EnumOrderField.COLOR
        )
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(listResult[1].msgId).isEqualTo(R.string.error_recover_list_note)
    }
}

