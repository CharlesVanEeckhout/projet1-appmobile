package ca.qc.cgodin.projet1

import androidx.lifecycle.LiveData
import ca.qc.cgodin.projet1.model.Succursale

class SuccursaleRepository(private val succusaleDao: SuccursaleDao) {
    // Room execute toutes les requêtes dans un thread séparé.
    // Observed LiveData will notify the observer when the data has changed.
    val allSuccursales: LiveData<List<Succursale>> = succusaleDao.getSuccursales()

    suspend fun insert(succursale: Succursale){
        succusaleDao.insert(succursale)
    }

    suspend fun delete(succursale: Succursale) {
        succusaleDao.delete(succursale)
    }

    suspend fun updateSuccursale(succursale: Succursale) {
        succusaleDao.updateSuccursale(succursale)
    }

    suspend fun getSuccursale(Aut: Int) {
        succusaleDao.getSuccursale(Aut)
    }
}