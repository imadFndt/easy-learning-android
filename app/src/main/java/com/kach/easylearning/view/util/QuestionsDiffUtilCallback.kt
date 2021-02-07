package com.kach.easylearning.view.util

import androidx.recyclerview.widget.DiffUtil
import com.kach.easylearning.data.model.EasyLearningQuestion

class QuestionsDiffUtilCallback(
    private val oldList: List<EasyLearningQuestion>,
    private val newList: List<EasyLearningQuestion>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}