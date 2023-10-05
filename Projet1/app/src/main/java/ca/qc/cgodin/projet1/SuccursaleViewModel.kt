package ca.qc.cgodin.projet1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projet1.model.Succursale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuccursaleViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: SuccursaleRepository
    // - Using LiveData and caching what getStudents returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allSuccursales: LiveData<List<Succursale>>
    init {
        val succursalesDao =
            SuccursaleRoomDatabase.getDatabase(application).succursaleDao()
        repository = SuccursaleRepository(succursalesDao)
        allSuccursales = repository.allSuccursales
    }

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(succursale: Succursale) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(succursale)
    }

    fun delete(succursale: Succursale) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(succursale)
    }

    fun update(succursale: Succursale) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateSuccursale(succursale)
    }

    fun getSuccursale(Aut: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSuccursale(Aut)
    }
}