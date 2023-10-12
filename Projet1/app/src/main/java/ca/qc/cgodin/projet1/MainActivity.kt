package ca.qc.cgodin.projet1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

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
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }




    }
}