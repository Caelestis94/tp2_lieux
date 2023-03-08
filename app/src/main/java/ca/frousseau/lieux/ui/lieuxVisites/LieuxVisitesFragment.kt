package ca.frousseau.lieux.ui.lieuxVisites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.LieuxVisitesAdapter
import ca.frousseau.lieux.data.LieuxVisitesDao
import ca.frousseau.lieux.databinding.FragmentLieuxVisitesBinding
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite
import kotlin.concurrent.thread

class LieuxVisitesFragment : Fragment() {

    private var _binding: FragmentLieuxVisitesBinding? = null
    private var listeLieuxVisites: List<Lieu> = listOf() // Liste des lieux visités
    private lateinit var lieuxVisitesAdapter: LieuxVisitesAdapter // Adapter de la liste des lieux visités
    private lateinit var lieuxVisitesViewModel: LieuxVisitesViewModel // ViewModel de la liste des lieux visités

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lieuxVisitesViewModel =
            ViewModelProvider(this).get(LieuxVisitesViewModel::class.java)

        _binding = FragmentLieuxVisitesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv_lieux_visites: RecyclerView =
            binding.rvLieuxVisites // Récupération du RecyclerView des lieux visités
        rv_lieux_visites.layoutManager = LinearLayoutManager(root.context)
        lieuxVisitesAdapter = LieuxVisitesAdapter(listeLieuxVisites)
        rv_lieux_visites.adapter = lieuxVisitesAdapter

        val onItemClickListener: LieuxVisitesAdapter.onItemClickListenerInterface =
            object : LieuxVisitesAdapter.onItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    deleteLieuVisite(listeLieuxVisites[position].id, position)
                }
            }

        lieuxVisitesAdapter.setOnItemClickListener(onItemClickListener)

        lieuxVisitesViewModel.getAllLieuxVisites().observe(viewLifecycleOwner) { lieux ->
            listeLieuxVisites = lieux
            lieuxVisitesAdapter.updateAdapter(lieux)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Supprime un lieu visité de la liste des lieux visités
     * @param lieuId : id du lieu visité à supprimer
     * @param position : position du lieu visité dans la liste
     */
    fun deleteLieuVisite(lieuId: Int, position: Int) {
        thread {
            lieuxVisitesViewModel.deleteLieuVisite(lieuId)
        }
        lieuxVisitesAdapter.notifyItemRemoved(position)
        Toast.makeText(context, "Lieu visité supprimé", Toast.LENGTH_SHORT).show()
    }
}