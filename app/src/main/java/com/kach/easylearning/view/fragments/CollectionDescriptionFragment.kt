package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.kach.easylearning.R
import com.kach.easylearning.activityViewModels
import com.kach.easylearning.databinding.CollectionsDescriptionFragmentBinding
import com.kach.easylearning.view.adapters.CollectionDescriptionListAdapter
import com.kach.easylearning.viewmodels.MainViewModel

class CollectionDescriptionFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: CollectionsDescriptionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CollectionsDescriptionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listAdapter = CollectionDescriptionListAdapter()
        with(binding.collectionQuestionsView) {
            adapter = listAdapter
            val decor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.collection_list_divider)
                    ?.let { setDrawable(it) }
            }
            addItemDecoration(decor)
        }
        binding.startFab.setOnClickListener { activityViewModel.requestTestStart() }
        activityViewModel.selectedCollection.observe(viewLifecycleOwner) { collection ->
            collection ?: return@observe
            with(binding) {
                collectionAuthorText.text = "TODO"
                collectionDescriptionText.text = collection.description
                collectionTitleText.text = collection.title
                collectionQuestionsAmountText.text = collection.questionsCount.toString()
            }
        }
        activityViewModel.questionList.observe(viewLifecycleOwner) { list ->
            binding.collectionQuestionsLayoutText.isVisible = list != null
            binding.collectionQuestionsView.isVisible = list != null
            binding.startFab.isVisible = list != null
            list?.let { listAdapter.setItems(it) }
        }
        activityViewModel.showQuestionsLoadError.observe(viewLifecycleOwner) { isError ->
            if (isError && !activityViewModel.isQuestionLoadErrorToastShown) {
                Toast.makeText(context, getString(R.string.failed_questions_load), Toast.LENGTH_SHORT).show()
                activityViewModel.isQuestionLoadErrorToastShown = true
            }
        }
    }
}