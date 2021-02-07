package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.databinding.CollectionDescriptionListItemBinding
import com.kach.easylearning.view.util.QuestionsDiffUtilCallback

class CollectionDescriptionListAdapter :
    BaseListAdapter<CollectionDescriptionListItemBinding, EasyLearningQuestion>() {

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<CollectionDescriptionListItemBinding, EasyLearningQuestion> {
        return CollectionDescriptionViewHolder(
            CollectionDescriptionListItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun getDiffUtilCallback(
        oldItems: List<EasyLearningQuestion>,
        newItems: List<EasyLearningQuestion>
    ): DiffUtil.Callback = QuestionsDiffUtilCallback(oldItems, newItems)

    class CollectionDescriptionViewHolder(private val binding: CollectionDescriptionListItemBinding) :
        BaseViewHolder<CollectionDescriptionListItemBinding, EasyLearningQuestion>(binding) {
        override fun bind(item: EasyLearningQuestion) {
            binding.questionText.text = item.data
        }
    }
}