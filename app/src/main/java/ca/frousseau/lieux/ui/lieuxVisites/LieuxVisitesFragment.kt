package ca.frousseau.lieux.ui.lieuxVisites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.frousseau.lieux.LieuxVisitesAdapter
import ca.frousseau.lieux.databinding.FragmentDashboardBinding

class LieuxVisitesFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lieuxVisitesViewModel =
            ViewModelProvider(this).get(LieuxVisitesViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv_lieux_visites : RecyclerView = binding.listeVisites

        rv_lieux_visites.layoutManager = LinearLayoutManager(root.context)

        val onItemClickListener: LieuxVisitesAdapter.onItemClickListenerInterface = object : LieuxVisitesAdapter.onItemClickListenerInterface {
            override fun onItemClick(itemView: View?, position: Int) {
            }
        }

        lieuxVisitesViewModel.getAllLieuxVisites().observe(viewLifecycleOwner) { lieux ->
            val adapter = LieuxVisitesAdapter(lieux)
            rv_lieux_visites.adapter = adapter
            adapter.setOnItemClickListener(onItemClickListener)
            adapter.notifyDataSetChanged()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}