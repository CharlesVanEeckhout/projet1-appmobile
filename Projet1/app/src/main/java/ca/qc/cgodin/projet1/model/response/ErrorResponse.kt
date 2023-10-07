package ca.qc.cgodin.projet1.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse (
    @SerializedName("statut")
    val statut: String,
    @SerializedName("error")
    val error: String?
) : Serializable