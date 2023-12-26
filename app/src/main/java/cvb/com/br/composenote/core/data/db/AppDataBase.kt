package cvb.com.br.composenote.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cvb.com.br.composenote.feature_note.data.db.dao.NoteDao
import cvb.com.br.composenote.feature_note.data.db.entity.NoteEntity

@Database(
    version = 1,
    entities = [ NoteEntity::class ],
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        val DATA_BASE_NAME: String = "NoteDataBase"
    }

    abstract val noteDao: NoteDao
}