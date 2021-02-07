package com.kach.easylearning.view.util

import androidx.recyclerview.widget.DiffUtil
import com.kach.easylearning.data.model.EasyLearningCollection

class CollectionsDiffUtilCallback(
    private val oldList: List<EasyLearningCollection>,
    private val newList: List<EasyLearningCollection>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}