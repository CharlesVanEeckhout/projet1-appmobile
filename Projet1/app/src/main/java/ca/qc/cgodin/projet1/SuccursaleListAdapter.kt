package ca.qc.cgodin.projet1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projet1.databinding.RecyclerviewItemBinding
import ca.qc.cgodin.projet1.model.Succursale

class SuccursaleListAdapter(
    context: Context
) : RecyclerView.Adapter<SuccursaleListAdapter.SuccursaleViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var succursales = emptyList<Succursale>() // Cached copy of succursales
    private lateinit var binding: RecyclerviewItemBinding

    private lateinit var onItemEditListener: ((Succursale) -> Unit)
    private lateinit var onItemDeleteListener: ((Succursale) -> Unit)

    inner class SuccursaleViewHolder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SuccursaleViewHolder {
        binding = RecyclerviewItemBinding.inflate(inflater, parent, false)

        return SuccursaleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuccursaleViewHolder, position: Int) {
        val current = succursales[position]
        holder.binding.tvVille.setText(current.Ville)
        holder.binding.tvBudget.setText(current.Budget.toString()) //TODO: signe de dollar

        holder.binding.btnEdit.setOnClickListener {
            onItemEditListener(current)
        }

        holder.binding.btnDelete.setOnClickListener {
            onItemDeleteListener(current)
        }
    }

    fun setSuccursales(succursales: List<Succursale>) {
        this.succursales = succursales
        Log.i("SuccursaleListAdapter", succursales.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount() = succursales.size

    fun setOnItemEditListener(listener: (Succursale) -> Unit){
        onItemEditListener = listener
    }
    fun setOnItemDeleteListener(listener: (Succursale) -> Unit){
        onItemDeleteListener = listener
    }
}