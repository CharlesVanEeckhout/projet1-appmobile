package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ca.qc.cgodin.projet1.databinding.FragmentNewSuccursaleBinding
import ca.qc.cgodin.projet1.model.Succursale
import ca.qc.cgodin.projet1.model.data.AjoutSuccursale
import ca.qc.cgodin.projet1.model.response.AjoutSuccursaleResponse
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
 * Use the [NewSuccursaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewSuccursaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNewSuccursaleBinding? = null
    private val binding get() = _binding!!
    private lateinit var application: Application;
    private val args: NewSuccursaleFragmentArgs by navArgs()

    private val succursaleViewModel: SuccursaleViewModel by lazy {
        ViewModelProvider(
            this,
            SuccursaleViewModelFactory(application)
        ).get(SuccursaleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = resources.getString(R.string.add_name)
        _binding = FragmentNewSuccursaleBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext as Application

        Log.i("Aut ", args.aut.toString())
        //set les deux textBox au départ
        binding.btnAjout.setOnClickListener {
            val ville = binding.tvVille.text.toString();
            val budget = binding.tvAjoutBudget.text.toString().toIntOrNull() ?: 0

            val addSuccursale = AjoutSuccursale(args.aut, ville, budget)
            val booVille = (ville.length in 2..15)
            val booBudget = (budget in 500..10000000)

            Log.i("Résultats : Budget", booBudget.toString())
            Log.i("Résultats : Ville", booBudget.toString())
            if (!booVille) {
                binding.tvErreurValidationAjouter.text = resources.getText(R.string.erreur_ajoutSuccursale_ville_invalide);
            }
            else if (!booBudget) {
                binding.tvErreurValidationAjouter.text = resources.getText(R.string.erreur_ajoutSuccursale_budget_invalide);
            }
            else {
            succursaleViewModel.ajoutSuccursale(addSuccursale,
                { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                    val responseJson: String = response.body()?.string() ?: "null"
                    val succursaleAjout: AjoutSuccursaleResponse =
                        Gson().fromJson(responseJson, AjoutSuccursaleResponse::class.java)
                    val error: ErrorResponse =
                        Gson().fromJson(responseJson, ErrorResponse::class.java)

                    if (error.error == null) {
                        when (succursaleAjout.statut) {
                            "OKI" -> {
                                val action =
                                    NewSuccursaleFragmentDirections.actionNewSuccursaleFragmentToListSuccursalesFragment(
                                        args.aut
                                    )
                                findNavController().navigate(action)
                                Toast.makeText(
                                    activity,
                                    resources.getText(R.string.feedback_ajoutSuccursale_valide),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            "OKM" -> {
                                val action =
                                    NewSuccursaleFragmentDirections.actionNewSuccursaleFragmentToListSuccursalesFragment(
                                        args.aut
                                    )
                                findNavController().navigate(action)
                                Toast.makeText(
                                    activity,
                                    resources.getText(R.string.feedback_modificationSuccursale_valide),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    activity,
                                    resources.getText(R.string.erreur_ajoutSuccursale_invalide),
                                    Toast.LENGTH_SHORT
                                ).show()
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

//            val succursale = Succursale(args.aut, ville, budget)
//            succursaleViewModel.insert(succursale)
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewSuccursaleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewSuccursaleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}