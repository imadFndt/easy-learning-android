package com.kach.tuts

import androidx.recyclerview.widget.DiffUtil
import com.kach.tuts.model.TutsCollectionTemp

class CollectionsDiffUtilCallback(
    private val oldList: List<TutsCollectionTemp>,
    private val newList: List<TutsCollectionTemp>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}