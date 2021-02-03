package com.kach.tuts

import androidx.recyclerview.widget.DiffUtil
import com.kach.tuts.model.TutsQuestion

class QuestionsDiffUtilCallback(
    private val oldList: List<TutsQuestion>,
    private val newList: List<TutsQuestion>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}