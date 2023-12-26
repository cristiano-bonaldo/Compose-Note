package cvb.com.br.composenote.feature_note.domain.use_case.note

import com.google.common.truth.Truth.assertThat
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


class DeleteNoteTest {
    @Mock
    lateinit var noteRepository: NoteRepository

    @InjectMocks
    lateinit var useCase: DeleteNote

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun deleteNote_Success() = runTest {
        val note = NoteFactory.createNote(id=1)

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).deleteNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(listResult[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun deleteNote_Error() = runTest {
        val note = NoteFactory.createNote(id=1)

        Mockito.doAnswer { throw Exception() }.`when`(noteRepository).deleteNote(note)

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).deleteNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(listResult[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(listResult[1].msgId).isEqualTo(R.string.error_delete_note)
    }
}