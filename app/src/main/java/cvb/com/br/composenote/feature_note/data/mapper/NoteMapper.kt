package cvb.com.br.composenote.feature_note.data.mapper

import cvb.com.br.composenote.feature_note.data.db.entity.NoteEntity
import cvb.com.br.composenote.feature_note.domain.model.Note

fun NoteEntity.toNote() =
    Note(
        id = this.id,
        title = this.title,
        info = this.info,
        timestamp = this.timestamp,
        color = color
    )

fun Note.toNoteEntity() =
    NoteEntity(
        id = this.id,
        title = this.title,
        info = this.info,
        timestamp = this.timestamp,
        color = color
    )
