package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kach.easylearning.R
import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.databinding.CollectionItemBinding
import com.kach.easylearning.view.util.CollectionsDiffUtilCallback

class CollectionListAdapter : BaseListAdapter<CollectionItemBinding, EasyLearningCollection>() {
    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<CollectionItemBinding, EasyLearningCollection> {
        return CollectionViewHolder(CollectionItemBinding.inflate(inflater, parent, false))
    }

    override fun getDiffUtilCallback(
        oldItems: List<EasyLearningCollection>,
        newItems: List<EasyLearningCollection>
    ): DiffUtil.Callback = CollectionsDiffUtilCallback(oldItems, newItems)

    class CollectionViewHolder(private val binding: CollectionItemBinding) :
        BaseViewHolder<CollectionItemBinding, EasyLearningCollection>(binding) {
        override fun bind(item: EasyLearningCollection) {
            with(binding) {
                collectionDescription.text = item.description
                val totalString = itemView.context.getString(R.string.total_questions, item.questionsCount)
                collectionQuestionsAmount.text = totalString
                collectionTitle.text = item.title
            }
        }
    }
}