package ca.qc.cgodin.projet1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projet1.model.Succursale

class SuccursaleListAdapter constructor(
    context: Context
) : RecyclerView.Adapter<SuccursaleListAdapter.SuccursaleViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var succursales = emptyList<Succursale>() // Cached copy of succursales

    inner class SuccursaleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SuccursaleViewHolder {
        val itemView = inflater.inflate(
            R.layout.recyclerview_item, parent,
            false
        )
        return SuccursaleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SuccursaleViewHolder, position: Int) {
        val current = succursales[position]

    }

    fun setSuccursales(succursales: List<Succursale>) {
        this.succursales = succursales
        notifyDataSetChanged()
    }

    override fun getItemCount() = succursales.size
}