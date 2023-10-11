package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding
import ca.qc.cgodin.projet1.model.response.ListeSuccursaleResponse
import ca.qc.cgodin.projet1.model.schema.ListeSuccursaleSchema
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

private const val ARG_AUT = "Aut"

/**
 * A simple [Fragment] subclass.
 * Use the [ListSuccursalesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListSuccursalesFragment : Fragment() {
    private var _aut: Long? = null
    private val aut get() = _aut!!

    private var _binding: FragmentListSuccursalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _aut = it.getLong(ARG_AUT,-1)
        }
        if(aut < 0 || aut > 1e12-1){
            throw Exception("aut invalide: " + aut.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListSuccursalesBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext  as Application

        //set les deux textBox au départ
        binding.btnAjouterVille.setOnClickListener {

        }

        val succursaleListAdapter = SuccursaleListAdapter(inflater.context)
        binding.recyclerView.adapter = succursaleListAdapter

        viewModel.listeSuccursale(ListeSuccursaleSchema(aut),
            { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                val responseJson: String = response.body()?.string() ?: "null"
                val listeSuccursale: ListeSuccursaleResponse = Gson().fromJson(responseJson, ListeSuccursaleResponse::class.java)
                succursaleListAdapter.setSuccursales(listeSuccursale.succursales)
            },
            { _: Call<ResponseBody>, t: Throwable ->
                // TODO: setText(resources.getText(R.string.erreur_timeout))
            })

        // TODO: setonclick boutons edit et delete des item du adapter


        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param aut Parameter 1.
         * @return A new instance of fragment ListSuccursalesFragment.
         */
        @JvmStatic
        fun newInstance(aut: Long) =
            ListSuccursalesFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_AUT, aut)
                }
            }
    }
}