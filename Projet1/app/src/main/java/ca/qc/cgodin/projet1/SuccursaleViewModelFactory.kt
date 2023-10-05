package ca.qc.cgodin.projet1

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SuccursaleViewModelFactory(private val application: Application)
    : ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SuccursaleViewModel::class.java)){
            SuccursaleViewModel(application) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}