package cvb.com.br.composenote.feature_note.domain.util

sealed class Resource<T>(val data: T? = null, val msgId: Int = -1, val msgInfo: String? = null) {
    class Loading<T>(data: T? = null, isLoading: Boolean = true) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, msgId: Int = -1, msgInfo: String) : Resource<T>(data, msgId, msgInfo)
}