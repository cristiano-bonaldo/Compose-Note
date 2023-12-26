package cvb.com.br.composenote.feature_note.util

import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor

object NoteFactory {

    fun createNote(
        id: Long? = null,
        title: String = "Title for Note",
        info: String = "Information for Note",
        timestamp: Long = System.currentTimeMillis(),
        color: EnumColor = EnumColor.COLOR_1
    ): Note = Note(
        id = id,
        title = title,
        info = info,
        timestamp = timestamp,
        color = color.id
    )

}