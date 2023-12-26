package cvb.com.br.composenote.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtil {

    fun getDateTime(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            "-"
        }
    }
}