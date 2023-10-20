package ca.qc.cgodin.projet1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "succursale_table", primaryKeys = ["Aut", "Ville"])
data class Succursale(
    @SerializedName("Aut")
    var Aut: Long,
    @SerializedName("Ville")
    val Ville: String,
    @SerializedName("Budget")
    val Budget: Int
) : Serializable