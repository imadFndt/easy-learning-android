package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.kach.easylearning.R
import com.kach.easylearning.activityViewModels
import com.kach.easylearning.databinding.CollectionListFragmentBinding
import com.kach.easylearning.view.adapters.CollectionListAdapter
import com.kach.easylearning.viewmodels.MainViewModel

class CollectionListFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
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
        binding.swipeRefresh.setColorSchemeResources(R.color.main)
        binding.swipeRefresh.setOnRefreshListener { activityViewModel.loadCollections() }
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
        activityViewModel.isLoadingCollections.observe(viewLifecycleOwner) { isLoading ->
            if (binding.swipeRefresh.isRefreshing != isLoading) binding.swipeRefresh.isRefreshing = isLoading
        }
        activityViewModel.showCollectionsLoadError.observe(viewLifecycleOwner) { isError ->
            if (isError && !activityViewModel.isCollectionLoadErrorToastShown) {
                Toast.makeText(context, getString(R.string.failed_collections_load), Toast.LENGTH_SHORT)
                    .show()
                activityViewModel.isCollectionLoadErrorToastShown = true
            }
        }
    }
}