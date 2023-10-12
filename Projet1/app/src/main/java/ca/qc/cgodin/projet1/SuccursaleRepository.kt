package ca.qc.cgodin.projet1

import androidx.lifecycle.LiveData
import ca.qc.cgodin.projet1.model.data.Login
import ca.qc.cgodin.projet1.model.Succursale
import ca.qc.cgodin.projet1.model.data.AjoutSuccursale
import ca.qc.cgodin.projet1.model.data.BudgetSuccursale
import ca.qc.cgodin.projet1.model.data.CompteSuccursale
import ca.qc.cgodin.projet1.model.data.ListeSuccursale
import ca.qc.cgodin.projet1.model.data.RetraitSuccursale
import ca.qc.cgodin.projet1.model.data.Student
import ca.qc.cgodin.projet1.model.data.SuppressionSuccursale
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

    suspend fun getSuccursale(Aut: Int, Ville: String) {
        succusaleDao.getSuccursale(Aut, Ville)
    }

    fun connexionStudent(
        body: Login,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.connexionStudent(
            body
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

    fun enregistrementStudent(
        body: Student,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.enregistrementStudent(
            body
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

    fun listeSuccursale(
        body: ListeSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.listeSuccursale(
            body
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

    fun compteSuccursale(
        body: CompteSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.compteSuccursale(
            body
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

    fun budgetSuccursale(
        body: BudgetSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.budgetSuccursale(
            body
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

    fun ajoutSuccursale(
        body: AjoutSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.ajoutSuccursale(
            body
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

    fun retraitSuccursale(
        body: RetraitSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.retraitSuccursale(
            body
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

    fun suppressionSuccursale(
        body: SuppressionSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        RetrofitInstance.retrofitService.suppressionSuccursale(
            body
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