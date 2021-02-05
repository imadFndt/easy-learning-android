package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kach.easylearning.R
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.databinding.QuestionItemBinding
import com.kach.easylearning.view.util.QuestionsDiffUtilCallback

class QuestionStackAdapter : RecyclerView.Adapter<QuestionStackAdapter.QuestionStackViewHolder>() {
    private var runningOutListener: (() -> Unit)? = null

    private val items = mutableListOf<EasyLearningQuestion>()

    var currentPosition: Int? = null
        set(value) {
            field = value
            value?.let { if (value >= items.size - 2) runningOutListener?.invoke() }
        }

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
        val sizeDiff = newItems.size - items.size
        items.clear()
        items.addAll(newItems)
        if (sizeDiff > 0) notifyItemRangeInserted(items.size, sizeDiff)
        //diff.dispatchUpdatesTo(this)
        currentPosition = if (currentPosition == null) 0 else currentPosition
    }

    fun setRunningOutListener(block: (() -> Unit)) {
        runningOutListener = block
    }

    class QuestionStackViewHolder(val binding: QuestionItemBinding) : RecyclerView.ViewHolder(binding.root)
}