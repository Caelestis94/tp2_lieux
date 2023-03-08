package ca.frousseau.lieux

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.model.Lieu

class LieuAdapter(private var lieux: List<Lieu>) :
    RecyclerView.Adapter<LieuAdapter.LieuViewHolder>() {

    lateinit var listener: onItemClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LieuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lieu_one_line, parent, false)
        return LieuViewHolder(view)
    }


    interface onItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)
        fun onItemDeleteClickListener(position: Int)
        fun onItemEditClickListener(itemView: View?, position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListenerInterface) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: LieuViewHolder, position: Int) {
        val lieu: Lieu = lieux[position]
        holder.nom.text = lieu.nom
        holder.adresse.text = lieu.adresse
        holder.description.text = lieu.description
        holder.image.setImageURI(lieu.image.toUri())
    }

    /**
     * Retourne le nombre d'éléments dans la liste
     */
    override fun getItemCount(): Int {
        return lieux.size
    }

    /**
     * Met à jour la liste des lieux et notifie le changement
     */
    fun updateAdapter(lieux: List<Lieu>) {
        this.lieux = lieux
        notifyDataSetChanged()
    }

    inner class LieuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nom: TextView = itemView.findViewById(R.id.nom_lieu)
        var adresse: TextView = itemView.findViewById(R.id.adresse_lieu)
        var description: TextView = itemView.findViewById(R.id.desc_lieu)
        var image: ImageView = itemView.findViewById(R.id.image_lieu)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position)
                }
            }
            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val admin = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                    .getBoolean("isAdmin", false) // vérifie si l'utilisateur est admin
                if (!admin) {
                    return@setOnCreateContextMenuListener
                }
                val position = adapterPosition
                val edit = menu.add(0, v.id, 0, R.string.action_modifier)
                val delete = menu.add(0, v.id, 0, R.string.action_supprimer)
                edit.setOnMenuItemClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemEditClickListener(itemView, position)
                    }
                    false
                }
                delete.setOnMenuItemClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDeleteClickListener(position)
                    }
                    false
                }
            }

        }
    }
}