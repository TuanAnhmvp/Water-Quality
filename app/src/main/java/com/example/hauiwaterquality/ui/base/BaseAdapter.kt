package com.example.hauiwaterquality.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hauiwaterquality.BR
import com.example.hauiwaterquality.ui.base.touch.DragItemTouchListener
import com.example.hauiwaterquality.ui.base.touch.DragVerticalTouchHelper
import com.example.hauiwaterquality.ui.base.touch.ItemTouchDrag
import java.util.*

abstract class BaseAdapter<T : Any>(
    private val layout: Int
) : RecyclerView.Adapter<BaseViewHolder>(), DragItemTouchListener {

    private lateinit var inflater: LayoutInflater
    private var annotationDrag: ItemTouchDrag? = null
    var list = mutableListOf<T>()
    var listener: BaseListener? = null

    init {
        val annotations = this::class.java.declaredAnnotations
        for (annotation in annotations) {
            when (annotation) {
                is ItemTouchDrag -> {
                    annotationDrag = annotation
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            layout,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, list[position])


            /*val context = root.context as LifecycleOwner
            lifecycleOwner = context
            executePendingBindings()*/
        }
    }


    override fun getItemCount(): Int = list.size ?: 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // drag drop item
        annotationDrag?.let {
            val callback = DragVerticalTouchHelper(this)
            ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
        }
    }

    override fun onMove(from: Int, to: Int) {
        list.let { list ->
            if (from < to) {
                for (i in from until to) {
                    Collections.swap(list, i, i + 1)
                    notifyItemChanged(i, list[i])
                    notifyItemChanged(i + 1, list[i])
                }
            } else {
                for (i in from downTo to + 1) {
                    Collections.swap(list, i, i - 1)
                    notifyItemChanged(i, list[i])
                    notifyItemChanged(i - 1, list[i])
                }
            }
            notifyItemMoved(from, to)
        }
    }

    fun submit(newData: List<T>?) {
        val new = newData?.toMutableList()
        this.list = new!!
        notifyDataSetChanged()

    }

}
