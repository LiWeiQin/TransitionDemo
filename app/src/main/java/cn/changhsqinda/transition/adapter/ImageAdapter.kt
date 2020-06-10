package cn.changhsqinda.transition.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cn.changhsqinda.transition.databinding.LayoutViewpagerImageBinding
import cn.changhsqinda.transition.viewModel.ViewModelSecond
import com.arcns.core.util.Event
import kotlinx.coroutines.*


@BindingAdapter(
    value = ["bindSecondViewModel", "bindImageData", "bindCurrItemPosition"],
    requireAll = true
)
fun bindSecondViewModel(
    viewPager: ViewPager2,
    viewModel: ViewModelSecond,
    data: IntArray?,
    currentItemPosition: Int = 0
) {
    if (viewPager.adapter == null) {
        viewPager.adapter = ImageAdapter(viewModel)
    }
    data?.let {
        (viewPager.adapter as ImageAdapter).submitList(it.toMutableList())
    }
    viewPager.setCurrentItem(currentItemPosition, false)
}

class ImageAdapter(private val viewModel: ViewModelSecond) :
    ListAdapter<Int, ImageViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutViewpagerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                })
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.itemView.tag = position.toString()
        holder.bindTo(getItem(position), position)
        if (position == viewModel.position) {
            viewModel.viewInvited.value = Event(true)
        }
    }
}

class ImageViewHolder(private val binding: LayoutViewpagerImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(resId: Int, position: Int) {
        binding.image.setImageResource(resId)
        binding.position = position
    }

}