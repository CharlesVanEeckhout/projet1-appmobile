package ca.qc.cgodin.projet1

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ca.qc.cgodin.projet1.databinding.FragmentEditFavoritesBinding
import ca.qc.cgodin.projet1.databinding.FragmentEditSuccursaleBinding
import ca.qc.cgodin.projet1.model.Succursale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFavoritesFragment : Fragment() {
    private val args: EditSuccursaleFragmentArgs by navArgs()

    private var _binding: FragmentEditFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var application : Application;
    private val viewModel: SuccursaleViewModel by navGraphViewModels(R.id.nav_graph)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = resources.getString(R.string.edit_name)
        _binding = FragmentEditFavoritesBinding.inflate(inflater, container, false)

        val view = binding.root
        application = activity?.applicationContext as Application

        binding.tvVilleFavModifiee.setText(args.ville)
        binding.tvBudgetFavModifie.setText(args.budget.toString())


        binding.btnFavModifiee.setOnClickListener {
            val strBudget = binding.tvBudgetFavModifie.text.toString()
            val strVille = binding.tvVilleFavModifiee.text.toString()

            Log.i("Edit", strBudget)
            if (strVille.isEmpty() || strVille.length < 2 || strVille.length > 15) {
                binding.tvErreurValidationFavModifier.text = resources.getText(R.string.erreur_ajoutSuccursale_ville_invalide)
                return@setOnClickListener
            }
            val intBudget: Int
            try {
                intBudget = strBudget.toInt()
            } catch (e: NumberFormatException) {
                binding.tvErreurValidationFavModifier.text = resources.getText(R.string.erreur_ajoutSuccursale_budget_invalide)
                return@setOnClickListener
            }
            if (intBudget < 500 || intBudget >= 10000000) {
                binding.tvErreurValidationFavModifier.text = resources.getText(R.string.erreur_ajoutSuccursale_budget_invalide)
                return@setOnClickListener
            }

            viewModel.getSuccursale(args.aut, strVille)
            Log.i("Valeur Succursale ", viewModel.oneSuccursale.toString())
            val succursale = viewModel.oneSuccursale
            val succursaleX = Succursale(args.aut, strVille, intBudget)
            viewModel.update(succursaleX)
            binding.tvErreurValidationFavModifier.text = resources.getText(R.string.feedback_modifierSuccursaleFav_valide)

            Toast.makeText(activity, resources.getText(R.string.feedback_modifierSuccursaleFav_valide), Toast.LENGTH_SHORT).show()

        }
            return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}