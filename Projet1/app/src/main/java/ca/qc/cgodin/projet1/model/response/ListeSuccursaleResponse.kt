package ca.qc.cgodin.projet1.model.response

import ca.qc.cgodin.projet1.model.Succursale
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListeSuccursaleResponse(
    @SerializedName("statut")
    val statut: String,
    @SerializedName("succursales")
    val succursales: List<Succursale>
) : Serializable
