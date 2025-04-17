package id.ac.unuja.sampel.server.api

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("nim")
    val nim: String,
    @SerializedName("kata_kunci")
    val password: String
)

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: TokenData? = null
) {
    data class TokenData(
        @SerializedName("token")
        val token: String,
        @SerializedName("token_type")
        val tokenType: String,
        @SerializedName("header")
        val header: String
    )
}

data class NoteRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)

data class NoteResponseList(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: List<Note> = emptyList()

) {
    data class Note(
        @SerializedName("id")
        val id: Int = -1,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("content")
        val content: String = ""
    )
}

data class NoteResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: Note? = null
) {
    data class Note(
        @SerializedName("id")
        val id: Int = -1,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("content")
        val content: String = ""
    )
}