package ca.qc.cgodin.projet1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.qc.cgodin.projet1.model.Succursale

@Dao
interface SuccursaleDao {
    @Query("SELECT * from succursale_table ORDER BY Ville ASC")
    fun getSuccursales(): LiveData<List<Succursale>>
    @Query("SELECT * from succursale_table WHERE Aut=(:Aut) ORDER BY Ville ASC")
    fun getSuccursalesFromAut(Aut: Int): LiveData<List<Succursale>>
    @Query("SELECT * FROM succursale_table WHERE Aut=(:Aut) AND Ville=(:Ville)")
    fun getSuccursale(Aut: Long, Ville: String): LiveData<Succursale?>
    @Delete
    fun delete(succursale: Succursale)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(succursale: Succursale)
    @Update
    fun updateSuccursale(succursale: Succursale)
    @Query("DELETE FROM succursale_table")
    fun deleteAll()
    @Query("DELETE FROM succursale_table WHERE Aut=(:Aut)")
    fun deleteAllFromAut(Aut: Int)
}