package cvb.com.br.composenote.feature_note.domain.use_case.note

import com.google.common.truth.Truth
import cvb.com.br.composenote.R
import cvb.com.br.composenote.feature_note.domain.repository.NoteRepository
import cvb.com.br.composenote.feature_note.domain.util.Resource
import cvb.com.br.composenote.feature_note.util.NoteFactory
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetNoteByIdTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    @InjectMocks
    lateinit var useCase: GetNoteById

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getNote_Success_ReturnNote() = runTest {
        val idNote = 1000L
        val note = NoteFactory.createNote(id = idNote)

        Mockito.`when`(noteRepository.getNoteById(idNote)).thenReturn(note)

        val listResult = useCase.invoke(idNote).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).getNoteById(idNote)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Success::class.java)

        Truth.assertThat(listResult[1].data).isNotNull()
        Truth.assertThat(listResult[1].data).isEqualTo(note)
    }

    @Test
    fun getNote_Success_ReturnNull() = runTest {
        val idNote = 1000L

        Mockito.`when`(noteRepository.getNoteById(idNote)).thenReturn(null)

        val listResult = useCase.invoke(idNote).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).getNoteById(idNote)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Success::class.java)

        Truth.assertThat(listResult[1].data).isNull()
    }

    @Test
    fun getNote_Error() = runTest {
        val idNote = 1000L

        Mockito.doAnswer { throw Exception() }.`when`(noteRepository).getNoteById(idNote)

        val listResult = useCase.invoke(idNote).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).getNoteById(idNote)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(listResult[1].msgId).isEqualTo(R.string.error_recover_note)
    }

}
