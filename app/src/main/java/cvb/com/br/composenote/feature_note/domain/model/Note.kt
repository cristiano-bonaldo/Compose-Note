package cvb.com.br.composenote.feature_note.domain.model

data class Note(
    val id: Long? = null,
    val title: String,
    val info: String,
    val timestamp: Long,
    val color: Int
)