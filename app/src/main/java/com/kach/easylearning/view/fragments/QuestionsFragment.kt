package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kach.easylearning.activityViewModels
import com.kach.easylearning.databinding.QuestionsFragmentBinding
import com.kach.easylearning.view.adapters.QuestionStackAdapter
import com.kach.easylearning.viewmodels.MainActivityViewModel

class QuestionsFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()

    private lateinit var binding: QuestionsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QuestionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stackAdapter = QuestionStackAdapter()
        binding.questionsStack.adapter = stackAdapter
        viewModel.questionList.observe(viewLifecycleOwner){ stackAdapter.setItems(it) }
    }
}