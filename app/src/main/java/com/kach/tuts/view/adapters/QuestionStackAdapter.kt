package com.kach.tuts.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kach.tuts.QuestionsDiffUtilCallback
import com.kach.tuts.R
import com.kach.tuts.databinding.QuestionItemBinding
import com.kach.tuts.model.TutsQuestion

class QuestionStackAdapter : RecyclerView.Adapter<QuestionStackAdapter.QuestionStackViewHolder>() {
    private val items = mutableListOf<TutsQuestion>()

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
                root.context.getString(R.string.question_number, holder.bindingAdapterPosition)
            questionDescriptionText.text = item.description
            questionDescriptionText.isVisible = item.description != null
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<TutsQuestion>) {
        val diff = DiffUtil.calculateDiff(QuestionsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    class QuestionStackViewHolder(val binding: QuestionItemBinding) : RecyclerView.ViewHolder(binding.root)
}