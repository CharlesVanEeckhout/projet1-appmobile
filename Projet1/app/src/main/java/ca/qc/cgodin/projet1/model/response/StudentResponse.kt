package ca.qc.cgodin.projet1.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StudentResponse(
    @SerializedName("Matricule")
    val Matricule: Int,
    @SerializedName("Nom")
    val Nom: String,
    @SerializedName("Prenom")
    val Prenom: String,
    @SerializedName("token")
    val token: String
) : Serializable