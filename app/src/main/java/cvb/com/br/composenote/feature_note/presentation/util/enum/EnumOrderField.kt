package cvb.com.br.composenote.feature_note.presentation.util.enum

import cvb.com.br.composenote.R

enum class EnumOrderField(val key:Int, val idString: Int) {

    DATE(0, R.string.sort_field_date),

    TITLE(1, R.string.sort_field_title),

    COLOR(2, R.string.sort_field_color)
}