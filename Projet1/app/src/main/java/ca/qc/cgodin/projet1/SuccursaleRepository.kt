package ca.qc.cgodin.projet1

import androidx.lifecycle.LiveData
import ca.qc.cgodin.projet1.model.schema.Login
import ca.qc.cgodin.projet1.model.Succursale
import ca.qc.cgodin.projet1.model.schema.AjoutSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.BudgetSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.CompteSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.ListeSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.RetraitSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.Student
import ca.qc.cgodin.projet1.model.schema.SuppressionSuccursaleSchema
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
        body: ListeSuccursaleSchema,
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
        body: CompteSuccursaleSchema,
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
        body: BudgetSuccursaleSchema,
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
        body: AjoutSuccursaleSchema,
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
        body: RetraitSuccursaleSchema,
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
        body: SuppressionSuccursaleSchema,
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