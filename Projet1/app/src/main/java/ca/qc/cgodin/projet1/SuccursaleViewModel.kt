package ca.qc.cgodin.projet1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ca.qc.cgodin.projet1.model.data.Login
import ca.qc.cgodin.projet1.model.Succursale
import ca.qc.cgodin.projet1.model.data.AjoutSuccursale
import ca.qc.cgodin.projet1.model.data.BudgetSuccursale
import ca.qc.cgodin.projet1.model.data.CompteSuccursale
import ca.qc.cgodin.projet1.model.data.ListeSuccursale
import ca.qc.cgodin.projet1.model.data.RetraitSuccursale
import ca.qc.cgodin.projet1.model.data.Student
import ca.qc.cgodin.projet1.model.data.SuppressionSuccursale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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

    fun connexionStudent(
        body: Login,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.connexionStudent(body, funOnResponse, funOnFailure)
    }

    fun enregistrementStudent(
        body: Student,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.enregistrementStudent(body, funOnResponse, funOnFailure)
    }

    fun listeSuccursale(
        body: ListeSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.listeSuccursale(body, funOnResponse, funOnFailure)
    }

    fun compteSuccursale(
        body: CompteSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.compteSuccursale(body, funOnResponse, funOnFailure)
    }

    fun budgetSuccursale(
        body: BudgetSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.budgetSuccursale(body, funOnResponse, funOnFailure)
    }

    fun ajoutSuccursale(
        body: AjoutSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.ajoutSuccursale(body, funOnResponse, funOnFailure)
    }

    fun retraitSuccursale(
        body: RetraitSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.retraitSuccursale(body, funOnResponse, funOnFailure)
    }

    fun suppressionSuccursale(
        body: SuppressionSuccursale,
        funOnResponse: ((Call<ResponseBody>, Response<ResponseBody>) -> Unit),
        funOnFailure: ((Call<ResponseBody>, Throwable) -> Unit)
    ) {
        repository.suppressionSuccursale(body, funOnResponse, funOnFailure)
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

    fun getSuccursale(Aut: Int, Ville: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSuccursale(Aut, Ville)
    }
}