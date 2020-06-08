package cn.changhsqinda.transition.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.changhsqinda.transition.databinding.LayoutGridLayoutItemBinding
import cn.changhsqinda.transition.viewModel.ViewModelFirst


@BindingAdapter(value = ["bindImageViewModel", "bindImageData"], requireAll = true)
fun bindImageViewModel(recyclerView: RecyclerView, viewModel: ViewModelFirst, data: IntArray?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = GridAdapter(viewModel)
    }
    data?.let {
        (recyclerView.adapter as GridAdapter).submitList(it.toMutableList())
    }
}

class GridAdapter(private val viewModel: ViewModelFirst) :
    ListAdapter<Int, GridViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            LayoutGridLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent,false).apply {
                viewModel = this@GridAdapter.viewModel
            })
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bindTo(getItem(position), position)
    }

}

class GridViewHolder(private val binding: LayoutGridLayoutItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(resId: Int, position: Int) {
        binding.cardImage.setImageResource(resId)
        binding.position = position
    }

}