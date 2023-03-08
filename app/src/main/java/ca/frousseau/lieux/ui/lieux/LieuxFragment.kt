package ca.frousseau.lieux.ui.lieux

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.EditLieuDialogFragment
import ca.frousseau.lieux.LieuAdapter
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.databinding.FragmentHomeBinding
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.model.LieuVisite
import kotlin.concurrent.thread

class LieuxFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lieuxViewModel =
            ViewModelProvider(this).get(LieuxViewModel::class.java)



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val buttonAdd = binding.addListe
        buttonAdd.setOnClickListener {
            val dialog = EditLieuDialogFragment(null,true)
            val fm : FragmentManager = requireActivity().supportFragmentManager

            dialog.show(fm, "fragment_edit_lieu_dialog")
        }

        val root: View = binding.root
        val rv_lieux : RecyclerView = binding.listeLieux
        rv_lieux.layoutManager = LinearLayoutManager(root.context)



        lieuxViewModel.getAllLieux().observe(viewLifecycleOwner) { lieux ->
            val adapter = LieuAdapter(lieux)
            rv_lieux.adapter = adapter
            adapter.notifyDataSetChanged()
            val onItemClickListener: LieuAdapter.onItemClickListenerInterface = object : LieuAdapter.onItemClickListenerInterface {
                val lieuVisiteDao = LieuDatabase.getInstace(root.context).lieuxVisiteDao()
                val lieuDao = LieuDatabase.getInstace(root.context).lieuDao()
                override fun onItemClick(itemView: View?, position: Int) {

                    var exist = false
                    thread {
                        val lieuVisite = lieuVisiteDao.getLieuVisiteByLieuId(lieux[position].id)
                        if (lieuVisite != null) {
                            exist = true
                        }
                    }.join()

                    if (exist) {
                        Toast.makeText(root.context, "Lieu déjà dans la liste des visites", Toast.LENGTH_SHORT).show()
                    }else{
                        thread{
                            lieuVisiteDao.insertLieuVisite(LieuVisite(lieuId = lieux[position].id))
                        }
                        Toast.makeText(root.context, "Lieu ajouté à la liste des visites", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onItemDeleteClickListener(position: Int) {
                    thread {
                        lieuDao.deleteLieu(lieux[position])
                    }
                }

                override fun onItemEditClickListener(itemView: View?, position: Int) {
                    val dialog = EditLieuDialogFragment(lieux[position],false)
                    val fm : FragmentManager = requireActivity().supportFragmentManager

                    dialog.show(fm, "fragment_edit_lieu_dialog")

                    // when the dialog is dismissed, update the list with the new data
                }

            }


            adapter.setOnItemClickListener(onItemClickListener)

        }













        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateLieu(lieu: Lieu, new: Boolean) {
        Toast.makeText(requireContext(), "Lieu mis à jour", Toast.LENGTH_SHORT).show()
    }
}