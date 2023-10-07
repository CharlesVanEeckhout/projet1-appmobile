package ca.qc.cgodin.projet1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "succursale_table")
data class Succursale(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("Aut")
    val Aut: Int,
    @SerializedName("Ville")
    val Ville: String,
    @SerializedName("Budget")
    val Budget: Int
) : Serializable