package ca.qc.cgodin.projet1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentConnexionBinding
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding
import ca.qc.cgodin.projet1.model.response.ConnexionStudentResponse
import ca.qc.cgodin.projet1.model.response.ErrorResponse
import ca.qc.cgodin.projet1.model.response.ListeSuccursaleResponse
import ca.qc.cgodin.projet1.model.schema.Login
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [ConnexionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConnexionFragment : Fragment() {

    private var _binding: FragmentConnexionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConnexionBinding.inflate(layoutInflater, container, false)

        binding.btnConnexion.setOnClickListener {
            //TODO: validation textbox
            binding.tvResultatConnexion.setText("...")
            val strId = binding.editID.text.toString()
            val strMdp = binding.editMDP.text.toString()
            if(strId.length != 7 || strMdp.length < 6){
                binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                return@setOnClickListener
            }
            val intId: Int
            try{ intId = strId.toInt() }
            catch (e: NumberFormatException){
                binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                return@setOnClickListener
            }
            val intPartieMdp: Int
            try{ intPartieMdp = strMdp.substring(strMdp.length-5).toInt() }
            catch (e: NumberFormatException){
                binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                return@setOnClickListener
            }

            val login = Login(intId, strMdp)
            viewModel.connexionStudent(login,
                { _: Call<ResponseBody>, response: Response<ResponseBody> ->
                    val responseJson: String = response.body()?.string() ?: "null"
                    val connexionStudent: ConnexionStudentResponse = Gson().fromJson(responseJson, ConnexionStudentResponse::class.java)
                    val error: ErrorResponse = Gson().fromJson(responseJson, ErrorResponse::class.java)
                    if(error.error == null){
                        if(connexionStudent.student == null){
                            binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_connexion_invalide))
                        }
                        else{
                            //TODO: change fragment à ListSuccursalesFragment, passe aut en paramètre
                            val aut = intId.toString().padStart(7, '0') +
                                    intPartieMdp.toString().padStart(5, '0')
                            binding.tvResultatConnexion.setText("connexion réussie")
                        }
                    }
                    else{

                        binding.tvResultatConnexion.setText("Erreur: " + error.error)
                    }
                },
                { _: Call<ResponseBody>, t: Throwable ->
                    binding.tvResultatConnexion.setText(resources.getText(R.string.erreur_timeout))
                })
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ConnexionFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ConnexionFragment()
    }
}