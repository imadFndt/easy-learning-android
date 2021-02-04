package com.kach.easylearning

import androidx.recyclerview.widget.DiffUtil
import com.kach.easylearning.model.EasyLearningCollectionTemp

class CollectionsDiffUtilCallback(
    private val oldList: List<EasyLearningCollectionTemp>,
    private val newList: List<EasyLearningCollectionTemp>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}