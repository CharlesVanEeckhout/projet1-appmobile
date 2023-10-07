package ca.qc.cgodin.projet1.model.response

import ca.qc.cgodin.projet1.model.Succursale
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompteSuccursaleResponse(
    @SerializedName("statut")
    val statut: String,
    @SerializedName("nbSuccursales")
    val nbSuccursales: Int
) : Serializable
