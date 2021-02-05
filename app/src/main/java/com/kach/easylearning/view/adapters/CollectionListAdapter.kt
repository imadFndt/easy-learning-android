package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kach.easylearning.view.util.CollectionsDiffUtilCallback
import com.kach.easylearning.R
import com.kach.easylearning.databinding.CollectionItemBinding
import com.kach.easylearning.data.model.EasyLearningCollection

class CollectionListAdapter : RecyclerView.Adapter<CollectionListAdapter.CollectionItemViewHolder>() {
    private var itemClickListener: ((EasyLearningCollection) -> Unit)? = null

    private val items: MutableList<EasyLearningCollection> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CollectionItemBinding.inflate(inflater, parent, false)
        return CollectionItemViewHolder(binding).apply {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) itemClickListener?.invoke(items[position])
            }
        }
    }

    override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
        val item = items[holder.bindingAdapterPosition]
        with(holder.binding) {
            collectionAuthor.text = "TODO"
            val totalString = holder.itemView.context.getString(R.string.total_questions, item.questionsCount)
            collectionQuestionsAmount.text = totalString
            collectionTitle.text = item.title
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<EasyLearningCollection>) {
        val diff = DiffUtil.calculateDiff(CollectionsDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(block: ((EasyLearningCollection) -> Unit)?) {
        itemClickListener = block
    }

    class CollectionItemViewHolder(val binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root)
}