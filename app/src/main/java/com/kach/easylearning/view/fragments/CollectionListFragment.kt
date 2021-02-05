package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.kach.easylearning.R
import com.kach.easylearning.activityViewModels
import com.kach.easylearning.databinding.CollectionListFragmentBinding
import com.kach.easylearning.view.adapters.CollectionListAdapter
import com.kach.easylearning.viewmodels.MainActivityViewModel

class CollectionListFragment : Fragment() {
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: CollectionListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CollectionListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = CollectionListAdapter()
        listAdapter.setItemClickListener { activityViewModel.setSelectedCollection(it) }
        with(binding.recycler) {
            adapter = listAdapter
            val decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.collection_list_divider)
                    ?.let { setDrawable(it) }
            }
            addItemDecoration(decor)
        }
        activityViewModel.collectionList.observe(viewLifecycleOwner) { listAdapter.setItems(it) }
    }
}