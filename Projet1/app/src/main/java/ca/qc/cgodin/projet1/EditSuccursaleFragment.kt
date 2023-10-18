package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentEditSuccursaleBinding
import ca.qc.cgodin.projet1.model.response.AjoutSuccursaleResponse
import ca.qc.cgodin.projet1.model.data.AjoutSuccursale
import ca.qc.cgodin.projet1.model.data.RetraitSuccursale
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [EditSuccursaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditSuccursaleFragment : Fragment() {
    private val args: EditSuccursaleFragmentArgs by navArgs()

    private var _binding: FragmentEditSuccursaleBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(args.aut < 0 || args.aut > 1e12-1){
            throw Exception("aut invalide: ${args.aut}")
        }
        if(args.ville == ""){
            throw Exception("ville invalide")
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditSuccursaleBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext as Application

        binding.tvVilleModifiee.setText(args.ville)
        binding.tvBudgetModifie.setText(args.budget.toString())

        binding.btnModifiee.setOnClickListener {
            val strBudget = binding.tvBudgetModifie.text.toString()
            val strVille = binding.tvVilleModifiee.text.toString()
            if(strVille.isEmpty() || strVille.length <2 || strVille.length > 15){
                binding.tvErreurValidationModifier.setText(resources.getText(R.string.erreur_ajoutSuccursale_ville_invalide))
                return@setOnClickListener
            }
            val intBudget: Int
            try{ intBudget = strBudget.toInt() }
            catch (e: NumberFormatException){
                binding.tvErreurValidationModifier.setText(resources.getText(R.string.erreur_ajoutSuccursale_budget_invalide))
                return@setOnClickListener
            }
            if(intBudget < 500 || intBudget >= 10000000){
                binding.tvErreurValidationModifier.setText(resources.getText(R.string.erreur_ajoutSuccursale_budget_invalide))
                return@setOnClickListener
            }

            // editer c'est juste enlever et ajouter
            if(args.ville != strVille){
                // enlève la vieille succursale
                val retraitSuccursaleSchema = RetraitSuccursale(args.aut, args.ville)
                viewModel.retraitSuccursale(retraitSuccursaleSchema,
                    { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                        /*
                        que le statut dans la réponse soit OK ou PASOK, on a maintenant
                        la garantie que la vieille succursale n'existe pas:
                        soit elle a été enlevée avec succès,
                        soit elle n'a pas pu être enlevée parce qu'elle n'a jamais existée.
                        */
                        // ajoute la nouvelle succursale
                        val ajoutSuccursaleSchema = AjoutSuccursale(args.aut, strVille, intBudget)
                        ajoutSuccursale(ajoutSuccursaleSchema)
                    },
                    { _: Call<ResponseBody>, t: Throwable ->
                        binding.tvErreurValidationModifier.setText(resources.getText(R.string.erreur_timeout))
                    })
            }
            else{
                // ajoute la nouvelle succursale en écrasant la vieille succursale
                val ajoutSuccursaleSchema = AjoutSuccursale(args.aut, strVille, intBudget)
                ajoutSuccursale(ajoutSuccursaleSchema)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    fun ajoutSuccursale(ajoutSuccursaleSchema: AjoutSuccursale){
        viewModel.ajoutSuccursale(ajoutSuccursaleSchema,
            { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                val responseJson: String = response.body()?.string() ?: "null"
                val ajoutSuccursale: AjoutSuccursaleResponse = Gson().fromJson(responseJson, AjoutSuccursaleResponse::class.java)
                if(ajoutSuccursale.statut == "PASOK"){
                    //rien n'a changé
                    val action = EditSuccursaleFragmentDirections
                        .actionEditSuccursaleFragmentToListSuccursalesFragment(
                            args.aut
                        )
                    findNavController().navigate(action)

                    Toast.makeText(activity, resources.getText(R.string.erreur_modificationSuccursale_invalide), Toast.LENGTH_SHORT).show()
                }
                else{
                    //modification réussie
                    val action = EditSuccursaleFragmentDirections
                        .actionEditSuccursaleFragmentToListSuccursalesFragment(
                            args.aut
                        )
                    findNavController().navigate(action)
                }
            },
            { _: Call<ResponseBody>, t: Throwable ->
                //TODO: binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
            }
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditSuccursaleFragment.
         */
        @JvmStatic
        fun newInstance() = EditSuccursaleFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}