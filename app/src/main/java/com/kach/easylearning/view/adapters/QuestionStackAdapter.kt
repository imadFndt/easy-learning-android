package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kach.easylearning.view.util.QuestionsDiffUtilCallback
import com.kach.easylearning.R
import com.kach.easylearning.databinding.QuestionItemBinding
import com.kach.easylearning.data.model.EasyLearningQuestion

class QuestionStackAdapter : RecyclerView.Adapter<QuestionStackAdapter.QuestionStackViewHolder>() {
    private val items = mutableListOf<EasyLearningQuestion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionStackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuestionItemBinding.inflate(inflater, parent, false)
        return QuestionStackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionStackViewHolder, position: Int) {
        val item = items[holder.bindingAdapterPosition]
        with(holder.binding) {
            questionText.text = item.data
            questionNumberText.text =
                root.context.getString(R.string.question_number, holder.bindingAdapterPosition + 1)
            questionDescriptionText.text = item.description
            questionDescriptionText.isVisible = item.description != null
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<EasyLearningQuestion>) {
        val diff = DiffUtil.calculateDiff(QuestionsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    class QuestionStackViewHolder(val binding: QuestionItemBinding) : RecyclerView.ViewHolder(binding.root)
}