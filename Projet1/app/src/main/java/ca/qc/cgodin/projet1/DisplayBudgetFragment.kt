package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentDisplayBudgetBinding
import ca.qc.cgodin.projet1.databinding.FragmentEditSuccursaleBinding
import ca.qc.cgodin.projet1.model.data.BudgetSuccursale
import ca.qc.cgodin.projet1.model.response.AjoutSuccursaleResponse
import ca.qc.cgodin.projet1.model.response.BudgetSuccursaleResponse
import ca.qc.cgodin.projet1.model.response.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayBudgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayBudgetFragment : Fragment() {
    private var _binding: FragmentDisplayBudgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val args: DisplayBudgetFragmentArgs by navArgs()

    private val succursaleViewModel: SuccursaleViewModel by lazy {
        ViewModelProvider(
            this,
            SuccursaleViewModelFactory(application)
        ).get(SuccursaleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(args.aut < 0 || args.aut > 1e12-1){
            throw Exception("aut invalide: ${args.aut}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayBudgetBinding.inflate(inflater, container, false)
        val view = binding.root
        application = activity?.applicationContext as Application

        binding.btnRechercher.setOnClickListener{
            val strVille = binding.editVilleRecherchee.text.toString()
            if(strVille.isEmpty() || strVille.length <2 || strVille.length > 15){
                binding.tvErreurBudget.setText(resources.getText(R.string.erreur_ajoutSuccursale_ville_invalide))
                return@setOnClickListener
            }

            val budgetSuccursale = BudgetSuccursale(args.aut, strVille)
            succursaleViewModel.budgetSuccursale(budgetSuccursale,
                { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                    val responseJson: String = response.body()?.string() ?: "null"
                    val budgetSuccursaleAfficher: BudgetSuccursaleResponse =
                        Gson().fromJson(responseJson, BudgetSuccursaleResponse::class.java)
                    val error: ErrorResponse =
                        Gson().fromJson(responseJson, ErrorResponse::class.java)

                    if (error.error == null) {
                        when (budgetSuccursaleAfficher.statut) {
                            "PASOK" -> {
                                binding.tvErreurBudget.text = resources.getText(R.string.erreur_afficherBudget_invalide)
                            }
                            "OK" -> {
                                binding.tvErreurBudget.text = resources.getString(R.string.feedback_afficherBudget_valide).format(budgetSuccursaleAfficher.succursale.Budget)
                            }
                        }
                    }
                },
                { _: Call<ResponseBody>, t: Throwable ->
                    Toast.makeText(
                        activity,
                        resources.getText(R.string.erreur_timeout),
                        Toast.LENGTH_SHORT
                    ).show()
                })
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplayBudgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DisplayBudgetFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}