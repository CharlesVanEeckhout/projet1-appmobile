package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding
import ca.qc.cgodin.projet1.model.response.ListeSuccursaleResponse
import ca.qc.cgodin.projet1.model.data.ListeSuccursale
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
        _binding = FragmentListSuccursalesBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext  as Application

        //set les deux textBox au départ
        binding.btnAjouterVille.setOnClickListener {

        }
        binding.btnDeconnexion.setOnClickListener {

        }

        val succursaleListAdapter = SuccursaleListAdapter(inflater.context)
        binding.recyclerView.adapter = succursaleListAdapter

        // TODO: setonclick boutons edit et delete des item du adapter



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
                Log.i("ListSuccursalesFragment miseAJourRecyclerView listeSuccursale", listeSuccursale.succursales.toString())

                /*for(succursale in listeSuccursale.succursales){
                    succursale.Aut = args.aut
                }*/
                (binding.recyclerView.adapter as SuccursaleListAdapter).setSuccursales(listeSuccursale.succursales)
            },
            { _: Call<ResponseBody>, t: Throwable ->
                binding.tvMsgRecycler.setText(resources.getText(R.string.erreur_timeout))
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