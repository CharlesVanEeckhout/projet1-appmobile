package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentEditSuccursaleBinding
import ca.qc.cgodin.projet1.model.response.AjoutSuccursaleResponse
import ca.qc.cgodin.projet1.model.response.ListeSuccursaleResponse
import ca.qc.cgodin.projet1.model.response.RetraitSuccursaleResponse
import ca.qc.cgodin.projet1.model.schema.AjoutSuccursaleSchema
import ca.qc.cgodin.projet1.model.schema.RetraitSuccursaleSchema
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [EditSuccursaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditSuccursaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _aut: Long? = null
    private val aut get() = _aut!!
    private lateinit var ville: String

    private var _binding: FragmentEditSuccursaleBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _aut = it.getLong(ARG_AUT, -1)
            ville = it.getString(ARG_VILLE, "")
        }
        if(aut < 0 || aut > 1e12-1){
            throw Exception("aut invalide: $aut")
        }
        if(ville == ""){
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

        binding.btnModifiee.setOnClickListener {
            val strBudget = binding.tbBudgetModifie.text.toString()
            val strVille = binding.tbVilleModifiee.text.toString()
            if(strVille.isEmpty()){
                //TODO: binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                return@setOnClickListener
            }
            val intBudget: Int
            try{ intBudget = strBudget.toInt() }
            catch (e: NumberFormatException){
                //TODO: binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                return@setOnClickListener
            }
            if(intBudget < 500 || intBudget >= 10000000){
                //TODO: budget invalide
                return@setOnClickListener
            }

            // editer c'est juste enlever et ajouter
            if(ville != strVille){
                // enlève la vieille succursale
                val retraitSuccursaleSchema = RetraitSuccursaleSchema(aut, ville)
                viewModel.retraitSuccursale(retraitSuccursaleSchema,
                    { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                        /*
                        que le statut dans la réponse soit OK ou PASOK, on a maintenant
                        la garantie que la vieille succursale n'existe pas:
                        soit elle a été enlevée avec succès,
                        soit elle n'a pas pu être enlevée parce qu'elle n'a jamais existée.
                        */
                        // ajoute la nouvelle succursale
                        val ajoutSuccursaleSchema = AjoutSuccursaleSchema(aut, strVille, intBudget)
                        ajoutSuccursale(ajoutSuccursaleSchema)
                    },
                    { _: Call<ResponseBody>, t: Throwable ->
                        //TODO: binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
                    })
            }
            else{
                // ajoute la nouvelle succursale en écrasant la vieille succursale
                val ajoutSuccursaleSchema = AjoutSuccursaleSchema(aut, strVille, intBudget)
                ajoutSuccursale(ajoutSuccursaleSchema)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    fun ajoutSuccursale(ajoutSuccursaleSchema: AjoutSuccursaleSchema){
        viewModel.ajoutSuccursale(ajoutSuccursaleSchema,
            { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                val responseJson: String = response.body()?.string() ?: "null"
                val ajoutSuccursale: AjoutSuccursaleResponse = Gson().fromJson(responseJson, AjoutSuccursaleResponse::class.java)
                if(ajoutSuccursale.statut == "PASOK"){
                    //rien n'a changé
                    TODO("rien n'a changé")
                }
                else{
                    //modification réussie
                    val bundle = Bundle().apply {
                        putSerializable(ListSuccursalesFragment.ARG_AUT, aut)
                    }
                    findNavController().navigate(
                        R.id.action_editSuccursaleFragment_to_listSuccursalesFragment,
                        bundle
                    )
                }
            },
            { _: Call<ResponseBody>, t: Throwable ->
                //TODO: binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
            }
        )
    }

    companion object {
        const val ARG_AUT = "Aut"
        const val ARG_VILLE = "Ville"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param aut Parameter 1.
         * @param ville Parameter 2.
         * @return A new instance of fragment EditSuccursaleFragment.
         */
        @JvmStatic
        fun newInstance(aut: Long, ville: String) =
            EditSuccursaleFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_AUT, aut)
                    putString(ARG_VILLE, ville)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}