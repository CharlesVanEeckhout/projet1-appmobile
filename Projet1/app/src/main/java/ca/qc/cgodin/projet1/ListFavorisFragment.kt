package ca.qc.cgodin.projet1

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentListFavorisBinding
import ca.qc.cgodin.projet1.databinding.FragmentListSuccursalesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFavorisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFavorisFragment : Fragment() {
    private var _binding: FragmentListFavorisBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)
    private val args: ListFavorisFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.title = resources.getString(R.string.favorite_name)

        _binding = FragmentListFavorisBinding.inflate(inflater, container, false)

        val succursaleListAdapter = SuccursaleListAdapter(inflater.context)
        binding.recycleViewFavoris.adapter = succursaleListAdapter

        viewModel.allSuccursales.observe(activity as LifecycleOwner, Observer { succursale ->
            succursale?.let { succursaleListAdapter.setSuccursales(it) }
        })

        succursaleListAdapter.setOnItemDeleteListener { succursale ->
            succursale.let { viewModel.delete(succursale)
                Toast.makeText(activity, resources.getText(R.string.feedback_retraitSuccursale_valide), Toast.LENGTH_SHORT).show()
            }
        }

        succursaleListAdapter.setOnItemEditListener { succursale ->
            val action = ListFavorisFragmentDirections.actionListFavorisFragmentToEditFavoritesFragment(succursale.Ville, succursale.Budget, args.aut)
            findNavController().navigate(action);
        }

        succursaleListAdapter.setOnItemAddFavoriteListener {

        }

        val view = binding.root
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListeFavorisFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFavorisFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}