package ca.qc.cgodin.projet1.network

import ca.qc.cgodin.projet1.model.Login
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SuccursaleApiService {
    @POST("/students/Connexion")
    fun connexionStudent(@Body body: Login): Call<ResponseBody>;
}