package cvb.com.br.composenote.feature_note.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import cvb.com.br.composenote.core.data.db.AppDataBase
import cvb.com.br.composenote.feature_note.data.db.dao.util.NoteEntityFactory
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    // Run on the Main Thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDataBase

    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        noteDao = db.noteDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertNote() = runTest {
        val noteEntity = NoteEntityFactory.createNote()

        noteDao.saveNoteEntity(noteEntity)

        val noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(1)

        val noteEntityFromDB = noteEntityListFromDB[0]

        assertThat(noteEntityFromDB.title).isEqualTo(noteEntity.title)
        assertThat(noteEntityFromDB.info).isEqualTo(noteEntity.info)
        assertThat(noteEntityFromDB.timestamp).isEqualTo(noteEntity.timestamp)
        assertThat(noteEntityFromDB.color).isEqualTo(noteEntity.color)
    }

    @Test
    fun updateNote() = runTest {
        val noteEntity = NoteEntityFactory.createNote()

        noteDao.saveNoteEntity(noteEntity)

        val noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(1)

        var noteEntityFromDB = noteEntityListFromDB[0]

        val noteEntityEdited = noteEntityFromDB.copy(
            title = "New title",
            info = "New information",
            timestamp = System.currentTimeMillis(),
            color = EnumColor.COLOR_5.id
        )

        noteDao.saveNoteEntity(noteEntityEdited)

        val id = noteEntityFromDB.id ?: -5000

        noteEntityFromDB = noteDao.getNoteEntityById(id)

        assertThat(noteEntityFromDB).isNotNull()
        assertThat(noteEntityFromDB.id).isEqualTo(id)
        assertThat(noteEntityFromDB.title).isEqualTo(noteEntityEdited.title)
        assertThat(noteEntityFromDB.info).isEqualTo(noteEntityEdited.info)
        assertThat(noteEntityFromDB.timestamp).isEqualTo(noteEntityEdited.timestamp)
        assertThat(noteEntityFromDB.color).isEqualTo(noteEntityEdited.color)
    }

    @Test
    fun deleteNote() = runTest {
        val noteEntity = NoteEntityFactory.createNote()

        noteDao.saveNoteEntity(noteEntity)

        var noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(1)

        val noteEntityFromDB = noteEntityListFromDB[0]

        noteDao.deleteNoteEntity(noteEntityFromDB)

        noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.isEmpty()).isTrue()
    }

    @Test
    fun findNoteByIdForValidIdReturnNote() = runTest {
        val noteEntity = NoteEntityFactory.createNote()

        noteDao.saveNoteEntity(noteEntity)

        val noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(1)

        var noteEntityFromDB = noteEntityListFromDB[0]

        val id = noteEntityFromDB.id ?: -5000

        noteEntityFromDB = noteDao.getNoteEntityById(id)

        assertThat(noteEntityFromDB).isNotNull()
        assertThat(noteEntityFromDB.id).isEqualTo(id)
        assertThat(noteEntityFromDB.title).isEqualTo(noteEntity.title)
        assertThat(noteEntityFromDB.info).isEqualTo(noteEntity.info)
        assertThat(noteEntityFromDB.timestamp).isEqualTo(noteEntity.timestamp)
        assertThat(noteEntityFromDB.color).isEqualTo(noteEntity.color)
    }

    @Test
    fun findNoteByIdForInvalidIdReturnNull() = runTest {
        val noteEntity = NoteEntityFactory.createNote()

        noteDao.saveNoteEntity(noteEntity)

        val noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(1)

        var noteEntityFromDB = noteEntityListFromDB[0]

        val id = (noteEntityFromDB.id ?: -5000) + 1 // Invalid ID

        noteEntityFromDB = noteDao.getNoteEntityById(id)

        assertThat(noteEntityFromDB).isNull()
    }

    @Test
    fun getListNote() = runTest {
        val listNoteEntity = listOf(
            NoteEntityFactory.createNote(title="Title A", info = "Info A", color = EnumColor.COLOR_1),
            NoteEntityFactory.createNote(title="Title B", info = "Info B", color = EnumColor.COLOR_2),
            NoteEntityFactory.createNote(title="Title C", info = "Info C", color = EnumColor.COLOR_3)
        )

        listNoteEntity.forEach { noteEntity ->
            noteDao.saveNoteEntity(noteEntity)
        }

        val noteEntityListFromDB = noteDao.getListNoteEntity().first()

        assertThat(noteEntityListFromDB.size).isEqualTo(listNoteEntity.size)

        var idx = 0
        assertThat(listNoteEntity[idx].title).isEqualTo(noteEntityListFromDB[idx].title)
        assertThat(listNoteEntity[idx].info).isEqualTo(noteEntityListFromDB[idx].info)
        assertThat(listNoteEntity[idx].color).isEqualTo(noteEntityListFromDB[idx].color)

        idx = 1
        assertThat(listNoteEntity[idx].title).isEqualTo(noteEntityListFromDB[idx].title)
        assertThat(listNoteEntity[idx].info).isEqualTo(noteEntityListFromDB[idx].info)
        assertThat(listNoteEntity[idx].color).isEqualTo(noteEntityListFromDB[idx].color)

        idx = 2
        assertThat(listNoteEntity[idx].title).isEqualTo(noteEntityListFromDB[idx].title)
        assertThat(listNoteEntity[idx].info).isEqualTo(noteEntityListFromDB[idx].info)
        assertThat(listNoteEntity[idx].color).isEqualTo(noteEntityListFromDB[idx].color)
    }
}

