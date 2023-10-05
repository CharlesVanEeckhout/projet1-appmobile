package ca.qc.cgodin.projet1

import androidx.lifecycle.LiveData
import ca.qc.cgodin.projet1.model.Login
import ca.qc.cgodin.projet1.model.Succursale
import ca.qc.cgodin.projet1.network.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun connexionStudent(
        login: Login,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.connexionStudent(
            login
        ).enqueue(
            object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    funOnResponse(call, response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    funOnFailure(call, t)
                }
            }
        )
    }
}