package ca.qc.cgodin.projet1.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RetraitSuccursaleResponse (
    @SerializedName("statut")
    val statut: String
) : Serializable