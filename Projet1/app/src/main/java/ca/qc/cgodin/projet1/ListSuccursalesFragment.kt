package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cgodin.projet1.databinding.FragmentEditSuccursaleBinding
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding

private const val ARG_AUT = "Aut"

/**
 * A simple [Fragment] subclass.
 * Use the [ListSuccursalesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListSuccursalesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var aut: Long? = null

    private var _binding: FragmentListSuccursalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            aut = it.getLong(ARG_AUT)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(aut: Long) =
            ListSuccursalesFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_AUT, aut)
                }
            }
    }
}