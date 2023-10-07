package ca.qc.cgodin.projet1.network

import ca.qc.cgodin.projet1.model.schema.AjoutSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.BudgetSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.CompteSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.ListeSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.Login
import ca.qc.cgodin.projet1.model.schema.RetraitSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.Student
import ca.qc.cgodin.projet1.model.schema.SuppressionSuccursaleSchema
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
    fun listeSuccursale(@Body body: ListeSuccursaleSchema): Call<ResponseBody>
    @POST("/succursales/Succursale-Compte")
    fun compteSuccursale(@Body body: CompteSuccursaleSchema): Call<ResponseBody>
    @POST("/succursales/Succursale-Budget")
    fun budgetSuccursale(@Body body: BudgetSuccursaleSchema): Call<ResponseBody>
    @POST("/succursales/Succursale-Ajout")
    fun ajoutSuccursale(@Body body: AjoutSuccursaleSchema): Call<ResponseBody>
    @DELETE("/succursales/Succursale-Retrait")
    fun retraitSuccursale(@Body body: RetraitSuccursaleSchema): Call<ResponseBody>
    @DELETE("/succursales/Succursale-Suppression")
    fun suppressionSuccursale(@Body body: SuppressionSuccursaleSchema): Call<ResponseBody>
}