package cvb.com.br.composenote.feature_note.domain.use_case.note

import android.content.Context
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

class SaveNoteTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    @InjectMocks
    lateinit var useCase: SaveNote

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun saveNote_Success() = runTest {
        val note = NoteFactory.createNote()

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).saveNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun saveNote_ErrorNoTitle() = runTest {
        val note = NoteFactory.createNote(title = "")

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(0)).saveNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(listResult[0].msgId).isEqualTo(R.string.error_invalid_title)
    }

    @Test
    fun saveNote_ErrorNoInfo() = runTest {
        val note = NoteFactory.createNote(info = "")

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(0)).saveNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(listResult[0].msgId).isEqualTo(R.string.error_invalid_info)
    }

    @Test
    fun saveNote_ErrorGeneric() = runTest {
        val note = NoteFactory.createNote()

        Mockito.doAnswer { throw Exception() }.`when`(noteRepository).saveNote(note)

        val listResult = useCase.invoke(note).toList()

        Mockito.verify(noteRepository, Mockito.times(1)).saveNote(note)
        Mockito.verifyNoMoreInteractions(noteRepository)

        Truth.assertThat(listResult[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(listResult[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(listResult[1].msgId).isEqualTo(R.string.error_save_note)
    }

}