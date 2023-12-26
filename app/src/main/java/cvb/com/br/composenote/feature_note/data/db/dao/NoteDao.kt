package cvb.com.br.composenote.feature_note.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cvb.com.br.composenote.feature_note.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getListNoteEntity(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note where id = :id")
    suspend fun getNoteEntityById(id: Long): NoteEntity

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNoteEntity(noteEntity: NoteEntity)
}