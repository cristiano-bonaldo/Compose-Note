package cvb.com.br.composenote.core.presentation.util

import android.content.Context
import cvb.com.br.composenote.R
import dagger.hilt.android.qualifiers.ApplicationContext

data class StateError(val msgId: Int = -1, val msgInfo: String? = null, val idDefaultMsg: Int = R.string.error_unexpected)

fun StateError.handleError(context: Context): String {
    val message = buildString {
        if (msgId >= 0 || msgInfo != null) {
            if (msgId >= 0) {
                append(context.getString(msgId))
            }
            if (msgInfo != null) {
                append(msgInfo)
            }
        } else {
            append(context.getString(idDefaultMsg))
        }
    }

    return message
}