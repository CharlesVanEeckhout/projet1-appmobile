package ca.qc.cgodin.projet1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cgodin.projet1.databinding.FragmentConnexionBinding
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding
import ca.qc.cgodin.projet1.model.schema.Login


/**
 * A simple [Fragment] subclass.
 * Use the [ConnexionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConnexionFragment : Fragment() {

    private var _binding: FragmentConnexionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SuccursaleViewModel // TODO: Jai oublie comment apporter le viewmodel dans le fragment, ca devrait etre ds labo 8

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
            //val login = Login(binding.editID.text, binding.editMDP.text)
            //viewModel.connexionStudent(...)
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