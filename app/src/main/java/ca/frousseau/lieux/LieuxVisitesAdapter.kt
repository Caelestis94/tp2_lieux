package ca.frousseau.lieux

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.model.Lieu

class LieuxVisitesAdapter (private val lieuxVisites: List<Lieu>): RecyclerView.Adapter<LieuxVisitesAdapter.VisiteViewHolder>(){

    lateinit var listener : onItemClickListenerInterface


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lieu_one_line, parent, false)
        return VisiteViewHolder(view)
    }

    fun setOnItemClickListener(listener: onItemClickListenerInterface) {
        this.listener = listener
    }

    interface onItemClickListenerInterface {
        fun onItemClick(itemView: View?,position: Int)
    }

    override fun onBindViewHolder(holder: VisiteViewHolder, position: Int) {
        val lieuVisite: Lieu = lieuxVisites[position]
        holder.nom.text = lieuVisite.nom
        holder.adresse.text = lieuVisite.adresse
        holder.description.text = lieuVisite.description
        holder.image.setImageURI(lieuVisite.image.toUri())
    }

    override fun getItemCount(): Int {
        return lieuxVisites.size
    }

    inner class VisiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nom: TextView = itemView.findViewById(R.id.nom_lieu)
        var adresse: TextView = itemView.findViewById(R.id.adresse_lieu)
        var description: TextView = itemView.findViewById(R.id.desc_lieu)
        var image: ImageView = itemView.findViewById(R.id.image_lieu)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onItemClick(itemView, position)
                }
            }
        }
    }

}