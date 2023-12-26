package cvb.com.br.composenote.feature_note.data.db.dao.util

import cvb.com.br.composenote.feature_note.data.db.entity.NoteEntity
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor

object NoteEntityFactory {

    fun createNote(
        id: Long? = null,
        title: String = "Title for Note",
        info: String = "Information for Note",
        timestamp: Long = System.currentTimeMillis(),
        color: EnumColor = EnumColor.COLOR_1
    ): NoteEntity = NoteEntity(
        id = id,
        title = title,
        info = info,
        timestamp = timestamp,
        color = color.id
    )

}