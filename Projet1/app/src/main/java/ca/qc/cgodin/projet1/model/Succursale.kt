package ca.qc.cgodin.projet1.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "succursale_table")
data class Succursale(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("aut")
    val Aut: Int,
    @SerializedName("ville")
    val Ville: String,
    @SerializedName("budget")
    val Budget: Int
) : Serializable