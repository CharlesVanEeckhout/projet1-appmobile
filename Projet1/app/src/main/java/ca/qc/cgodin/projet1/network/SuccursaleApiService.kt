package ca.qc.cgodin.projet1.network

import ca.qc.cgodin.projet1.model.data.AjoutSuccursale
import ca.qc.cgodin.projet1.model.data.BudgetSuccursale
import ca.qc.cgodin.projet1.model.data.CompteSuccursale
import ca.qc.cgodin.projet1.model.data.ListeSuccursale
import ca.qc.cgodin.projet1.model.data.Login
import ca.qc.cgodin.projet1.model.data.RetraitSuccursale
import ca.qc.cgodin.projet1.model.data.Student
import ca.qc.cgodin.projet1.model.data.SuppressionSuccursale
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface SuccursaleApiService {
    @POST("/students/Connexion")
    fun connexionStudent(@Body body: Login): Call<ResponseBody>
    @POST("/students/Enregistrement")
    fun enregistrementStudent(@Body body: Student): Call<ResponseBody>
    @POST("/succursales/Succursale-Liste")
    fun listeSuccursale(@Body body: ListeSuccursale): Call<ResponseBody>
    @POST("/succursales/Succursale-Compte")
    fun compteSuccursale(@Body body: CompteSuccursale): Call<ResponseBody>
    @POST("/succursales/Succursale-Budget")
    fun budgetSuccursale(@Body body: BudgetSuccursale): Call<ResponseBody>
    @POST("/succursales/Succursale-Ajout")
    fun ajoutSuccursale(@Body body: AjoutSuccursale): Call<ResponseBody>
    @DELETE("/succursales/Succursale-Retrait")
    fun retraitSuccursale(@Body body: RetraitSuccursale): Call<ResponseBody>
    @DELETE("/succursales/Succursale-Suppression")
    fun suppressionSuccursale(@Body body: SuppressionSuccursale): Call<ResponseBody>
}