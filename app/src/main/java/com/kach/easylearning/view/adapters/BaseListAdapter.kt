package com.kach.easylearning.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : ViewBinding, K> :
    RecyclerView.Adapter<BaseListAdapter.BaseViewHolder<T, K>>() {
    private var itemClickListener: ((K) -> Unit)? = null

    private val items: MutableList<K> = mutableListOf()

    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<T, K>

    abstract fun getDiffUtilCallback(oldItems: List<K>, newItems: List<K>): DiffUtil.Callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, K> {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(inflater, parent).apply {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) itemClickListener?.invoke(items[position])
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, K>, position: Int) {
        holder.bind(items[holder.bindingAdapterPosition])
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<K>) {
        val diff = DiffUtil.calculateDiff(getDiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(block: ((K) -> Unit)?) {
        itemClickListener = block
    }

    abstract class BaseViewHolder<T : ViewBinding, K>(binding: T) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: K)
    }
}