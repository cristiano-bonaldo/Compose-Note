package cvb.com.br.composenote.feature_note.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val id: Long? = null,

    val title: String,
    val info: String,
    val timestamp: Long,
    val color: Int
)