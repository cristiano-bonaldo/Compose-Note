package cvb.com.br.composenote.feature_note.data.repository

import com.google.common.truth.Truth.assertThat
import cvb.com.br.composenote.feature_note.domain.data_source.NoteDS
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderField
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumOrderType
import cvb.com.br.composenote.feature_note.util.NoteFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NoteRepositoryImplTest {

    @Mock
    lateinit var noteDS: NoteDS

    @InjectMocks
    lateinit var noteRepositoryImpl: NoteRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun saveNote_Success() = runTest {
        val note = NoteFactory.createNote()

        noteRepositoryImpl.saveNote(note)

        Mockito.verify(noteDS, Mockito.times(1)).saveNote(note)
        Mockito.verifyNoMoreInteractions(noteDS)
    }

    @Test
    fun deleteNote_Success() = runTest {
        val note = NoteFactory.createNote()

        noteRepositoryImpl.deleteNote(note)

        Mockito.verify(noteDS, Mockito.times(1)).deleteNote(note)
        Mockito.verifyNoMoreInteractions(noteDS)
    }

    @Test
    fun getNoteByID_ReturnNote() = runTest {
        val noteId = 1000L
        val note = NoteFactory.createNote(id = noteId)

        Mockito.`when`(noteDS.getNoteById(noteId)).thenReturn(note)

        val noteResult = noteRepositoryImpl.getNoteById(noteId)

        Mockito.verify(noteDS, Mockito.times(1)).getNoteById(noteId)
        Mockito.verifyNoMoreInteractions(noteDS)

        assertThat(noteResult).isNotNull()
        assertThat(noteResult).isEqualTo(note)
    }

    @Test
    fun getNoteByID_ReturnNull() = runTest {
        val noteId = 1000L
        val note = null

        Mockito.`when`(noteDS.getNoteById(noteId)).thenReturn(note)

        val noteResult = noteRepositoryImpl.getNoteById(noteId)

        Mockito.verify(noteDS, Mockito.times(1)).getNoteById(noteId)
        Mockito.verifyNoMoreInteractions(noteDS)

        assertThat(noteResult).isNull()
    }

    @Test
    fun getListNote_OrderedByColorAscending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.ASCENDING, EnumOrderField.COLOR).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedBy { note -> note.color }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    @Test
    fun getListNote_OrderedByColorDescending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.DESCENDING, EnumOrderField.COLOR).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedByDescending { note -> note.color }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    @Test
    fun getListNote_OrderedByDateAscending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.ASCENDING, EnumOrderField.DATE).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedBy { note -> note.timestamp }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    @Test
    fun getListNote_OrderedByDateDescending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.DESCENDING, EnumOrderField.DATE).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedByDescending { note -> note.timestamp }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    @Test
    fun getListNote_OrderedByTitleAscending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.ASCENDING, EnumOrderField.TITLE).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedBy { note -> note.title }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    @Test
    fun getListNote_OrderedByTitleDescending() = runTest {
        val listNote = getListNote()

        val flow: Flow<List<Note>> = flowOf(listNote)
        Mockito.`when`(noteDS.getListNote()).thenReturn(flow)

        val listNoteResult = noteRepositoryImpl.getListNote(EnumOrderType.DESCENDING, EnumOrderField.TITLE).first()

        Mockito.verify(noteDS, Mockito.times(1)).getListNote()
        Mockito.verifyNoMoreInteractions(noteDS)

        val listNoteOrdered = getListNote().sortedByDescending { note -> note.title }

        assertThat(listNoteResult.size).isEqualTo(5)
        assertThat(listNoteResult[0]).isEqualTo(listNoteOrdered[0])
        assertThat(listNoteResult[1]).isEqualTo(listNoteOrdered[1])
        assertThat(listNoteResult[2]).isEqualTo(listNoteOrdered[2])
        assertThat(listNoteResult[3]).isEqualTo(listNoteOrdered[3])
        assertThat(listNoteResult[4]).isEqualTo(listNoteOrdered[4])
    }

    private fun getListNote() = listOf(
        Note(id = 1, title = "Title A", info = "Info A", timestamp = 2000, color = EnumColor.COLOR_1.id),
        Note(id = 3, title = "Title E", info = "Info E", timestamp = 3000, color = EnumColor.COLOR_4.id),
        Note(id = 5, title = "Title D", info = "Info D", timestamp = 1000, color = EnumColor.COLOR_3.id),
        Note(id = 4, title = "Title C", info = "Info C", timestamp = 5000, color = EnumColor.COLOR_5.id),
        Note(id = 2, title = "Title B", info = "Info B", timestamp = 4000, color = EnumColor.COLOR_2.id),
    )
}
