package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding
import ca.qc.cgodin.projet1.model.response.ListeSuccursaleResponse
import ca.qc.cgodin.projet1.model.data.ListeSuccursale
import ca.qc.cgodin.projet1.model.data.RetraitSuccursale
import ca.qc.cgodin.projet1.model.response.ErrorResponse
import ca.qc.cgodin.projet1.model.response.RetraitSuccursaleResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ListSuccursalesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListSuccursalesFragment : Fragment() {
    private val args: ListSuccursalesFragmentArgs by navArgs()

    private var _binding: FragmentListSuccursalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)

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
        activity?.title = resources.getString(R.string.app_name)
        _binding = FragmentListSuccursalesBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext  as Application

        //set les deux textBox au départ
        binding.btnAjouterVille.setOnClickListener {
            val action = ListSuccursalesFragmentDirections.actionListSuccursalesFragmentToNewSuccursaleFragment(args.aut)
            findNavController().navigate(action)
        }
        binding.btnDeconnexion.setOnClickListener {
            findNavController().navigate(R.id.action_listSuccursalesFragment_to_connexionFragment)
        }

        binding.btnAfficherFavoris.setOnClickListener {
            findNavController().navigate(R.id.action_listSuccursalesFragment_to_listFavorisFragment)
        }

        binding.btnAfficherBudget.setOnClickListener {
            val action = ListSuccursalesFragmentDirections.actionListSuccursalesFragmentToDisplayBudgetFragment(args.aut)
            findNavController().navigate(action)
        }

        val succursaleListAdapter = SuccursaleListAdapter(inflater.context)
        binding.recyclerView.adapter = succursaleListAdapter

        Log.i("Count", succursaleListAdapter.itemCount.toString())
        // TODO: setonclick boutons edit et delete des item du adapter
        succursaleListAdapter.setOnItemEditListener {
            val action = ListSuccursalesFragmentDirections.actionListSuccursalesFragmentToEditSuccursaleFragment(it.Ville, it.Budget, args.aut)
            findNavController().navigate(action)
        }

        succursaleListAdapter.setOnItemAddFavoriteListener { succursale ->
            succursale.let {
                succursale.Aut = args.aut
                viewModel.insert(succursale)
                Toast.makeText(activity, resources.getText(R.string.feedback_ajoutSuccursaleFav_valide), Toast.LENGTH_SHORT).show()
                Log.i("Succursale", succursale.toString())
            }
        }

        succursaleListAdapter.setOnItemDeleteListener {
            val succursaleRemove = RetraitSuccursale(args.aut, it.Ville)
            viewModel.retraitSuccursale(succursaleRemove,
                { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                    val responseJson: String = response.body()?.string() ?: "null"
                    val retraitSuccursale: RetraitSuccursaleResponse = Gson().fromJson(responseJson, RetraitSuccursaleResponse::class.java)
                    val error: ErrorResponse = Gson().fromJson(responseJson, ErrorResponse::class.java)
                    if(error.error == null){
                        when (retraitSuccursale.statut) {
                            "PASOK" -> {
//                                binding.tvMsgRecycler.text = resources.getText(R.string.erreur_retraitSuccursale_invalide)
                                Toast.makeText(activity, resources.getText(R.string.erreur_retraitSuccursale_invalide), Toast.LENGTH_SHORT).show()
                            }
                            "OK" -> {
//                                binding.tvMsgRecycler.text = resources.getText(R.string.feedback_retraitSuccursale_valide)
                                Toast.makeText(activity, resources.getText(R.string.feedback_retraitSuccursale_valide), Toast.LENGTH_SHORT).show()
                                miseAJourRecyclerView()
                            }
                        }
                    }
                    else{

//                        binding.tvMsgRecycler.text = error.error
                        Toast.makeText(activity, error.error, Toast.LENGTH_SHORT).show()
                    }
                },
                { _: Call<ResponseBody>, t: Throwable ->
//                    binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
                    Toast.makeText(activity, resources.getText(R.string.erreur_timeout), Toast.LENGTH_SHORT).show()
                })
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onStart() {
        super.onStart()
        miseAJourRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun miseAJourRecyclerView() {
        binding.tvMsgRecycler.setText("")
        viewModel.listeSuccursale(ListeSuccursale(args.aut),
            { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                val responseJson: String = response.body()?.string() ?: "null"
                Log.i("ListSuccursalesFragment miseAJourRecyclerView responseJson", responseJson)
                val listeSuccursale: ListeSuccursaleResponse = Gson().fromJson(responseJson, ListeSuccursaleResponse::class.java)
                for(succursale in listeSuccursale.succursales){
                    succursale.Aut = args.aut
                }

                Log.i("ListSuccursalesFragment miseAJourRecyclerView listeSuccursale", listeSuccursale.succursales.toString())

                (binding.recyclerView.adapter as SuccursaleListAdapter).setSuccursales(listeSuccursale.succursales)
            },
            { _: Call<ResponseBody>, t: Throwable ->
//                binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
                Toast.makeText(activity, resources.getText(R.string.erreur_timeout), Toast.LENGTH_SHORT).show()
            })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListSuccursalesFragment.
         */
        @JvmStatic
        fun newInstance() = ListSuccursalesFragment()
    }
}