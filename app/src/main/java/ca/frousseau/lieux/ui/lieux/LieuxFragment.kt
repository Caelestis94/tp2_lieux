package ca.frousseau.lieux.ui.lieux

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.EditLieuDialogFragment
import ca.frousseau.lieux.LieuAdapter
import ca.frousseau.lieux.databinding.FragmentLieuxBinding
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite
import ca.frousseau.lieux.ui.lieuxVisites.LieuxVisitesViewModel
import kotlin.concurrent.thread

class LieuxFragment : Fragment() {

    private var _binding: FragmentLieuxBinding? = null
    private lateinit var lieuxViewModel: LieuxViewModel // ViewModel pour les lieux
    private lateinit var lieuxVisitesViewModel: LieuxVisitesViewModel // ViewModel pour les lieux visités
    private var listeLieux: List<Lieu> = listOf() // Liste des lieux
    private lateinit var lieuAdapter: LieuAdapter // Adapter pour la liste des lieux
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lieuxViewModel =
            ViewModelProvider(this).get(LieuxViewModel::class.java)
        lieuxVisitesViewModel =
            ViewModelProvider(this).get(LieuxVisitesViewModel::class.java)


        _binding = FragmentLieuxBinding.inflate(inflater, container, false)

        val buttonAdd = binding.addListe // Boutton ajouter lieu
        val root: View = binding.root
        val rv_lieux: RecyclerView = binding.listeLieux // RecyclerView des lieux

        rv_lieux.layoutManager = LinearLayoutManager(root.context)
        rv_lieux.itemAnimator = DefaultItemAnimator()
        rv_lieux.setHasFixedSize(true)


        // Event listener pour boutton ajouter lieu
        buttonAdd.setOnClickListener {
            val dialog = EditLieuDialogFragment(null, true)
            val fm: FragmentManager = requireActivity().supportFragmentManager
            dialog.show(fm, "fragment_edit_lieu_dialog")
        }

        val onItemClickListener: LieuAdapter.onItemClickListenerInterface =
            object : LieuAdapter.onItemClickListenerInterface {

                override fun onItemClick(itemView: View?, position: Int) {
                    var estDejaVisite = false  // Si le lieu est déjà dans la liste des visites
                    thread {
                        estDejaVisite = lieuxVisitesViewModel.isLieuVisite(listeLieux[position].id)
                    }.join()

                    if (estDejaVisite) {
                        Toast.makeText(
                            root.context,
                            "Lieu a déjà été visité",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        thread {
                            lieuxVisitesViewModel.insertLieuVisite(LieuVisite(lieuId = listeLieux[position].id))
                        }
                        Toast.makeText(
                            root.context,
                            "Lieu ajouté à la liste des visites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onItemDeleteClickListener(position: Int) {
                    deleteLieu(listeLieux[position], position)
                }

                override fun onItemEditClickListener(itemView: View?, position: Int) {
                    val dialog = EditLieuDialogFragment(listeLieux[position], false)
                    val fm: FragmentManager = requireActivity().supportFragmentManager
                    dialog.show(fm, "fragment_edit_lieu_dialog")
                }

            }


        lieuAdapter = LieuAdapter(listeLieux)
        lieuAdapter.setOnItemClickListener(onItemClickListener)
        rv_lieux.adapter = lieuAdapter

        lieuxViewModel.getAllLieux().observe(viewLifecycleOwner) { lieux ->
            listeLieux = lieux
            lieuAdapter.updateAdapter(lieux) // Met à jour la liste des lieux dans l'adapter
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Supprime un lieu de la liste
     * @param lieu Lieu à supprimer
     * @param position Position du lieu dans la liste
     * @param view Vue
     */
    fun deleteLieu(lieu: Lieu, position: Int) {
        thread {
            lieuxViewModel.deleteLieu(lieu)
        }
        lieuAdapter.notifyItemRemoved(position)
        Toast.makeText(context, "Lieu supprimé", Toast.LENGTH_SHORT).show()
    }
}