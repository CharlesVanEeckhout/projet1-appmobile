package ca.qc.cgodin.projet1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ca.qc.cgodin.projet1.model.Login
import ca.qc.cgodin.projet1.network.RetrofitInstance
import ca.qc.cgodin.projet1.network.SuccursaleApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var succursaleViewModel: SuccursaleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val succursaleViewModelFactory = SuccursaleViewModelFactory(application)
        try {
            val succursaleViewModelProvider = ViewModelProvider(
                this,
                succursaleViewModelFactory)
            succursaleViewModel = succursaleViewModelProvider[SuccursaleViewModel::class.java]
            succursaleViewModel.connexionStudent(Login(3335555, "Secret88888"),
                { call: Call<ResponseBody>, response: Response<ResponseBody> ->
                    Log.i("funOnResponse call", call.toString())
                    Log.i("funOnResponse response", response.toString())
                    Log.i("funOnResponse response body", response.body()?.string() ?: "null")
                },
                { call: Call<ResponseBody>, t: Throwable ->
                    Log.i("funOnResponse call", call.toString())
                    Log.i("funOnResponse t", t.toString())
                })
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }




    }
}