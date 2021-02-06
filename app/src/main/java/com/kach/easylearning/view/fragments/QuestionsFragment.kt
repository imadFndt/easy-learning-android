package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kach.easylearning.R
import com.kach.easylearning.activityViewModels
import com.kach.easylearning.databinding.QuestionsFragmentBinding
import com.kach.easylearning.view.adapters.QuestionStackAdapter
import com.kach.easylearning.viewmodels.MainViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class QuestionsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

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
        stackAdapter.setRunningOutListener { viewModel.shuffleQuestions() }
        binding.questionsStack.adapter = stackAdapter
        binding.questionsStack.layoutManager = CardStackLayoutManager(context, object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                //NO-OP
            }

            override fun onCardSwiped(direction: Direction?) {
                //NO-OP
            }

            override fun onCardRewound() {
                //NO-OP
            }

            override fun onCardCanceled() {
                //NO-OP
            }

            override fun onCardAppeared(view: View?, position: Int) {
                stackAdapter.currentPosition = position
                binding.questionNumber.text = getString(R.string.question_number, position + 1)
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                //NO-OP
            }

        })
        viewModel.questionList.observe(viewLifecycleOwner) {
            binding.questionsStack.post { stackAdapter.setItems(it) }
        }
        viewModel.timer.observe(viewLifecycleOwner) { time ->
            time ?: return@observe
            val minutes = time / 60
            val seconds = time % 60
            val minutesString = if (minutes > 9) minutes else "0$minutes"
            val secondsString = if (seconds > 9) seconds else "0$seconds"
            binding.questionTimer.text = getString(R.string.timer_state, minutesString, secondsString)
        }
        viewModel.runTimer()
    }
}