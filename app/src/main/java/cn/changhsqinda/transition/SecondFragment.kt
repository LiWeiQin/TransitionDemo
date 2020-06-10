package cn.changhsqinda.transition

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import cn.changhsqinda.transition.databinding.FragmentSecondBinding
import cn.changhsqinda.transition.viewModel.ViewModelActivityMain
import cn.changhsqinda.transition.viewModel.ViewModelSecond
import com.arcns.core.util.EventObserver
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val viewModel by viewModels<ViewModelSecond>()
    private lateinit var binding: FragmentSecondBinding
    private val viewModelMain by activityViewModels<ViewModelActivityMain>()
    private val args by navArgs<SecondFragmentArgs>()
    private var pagePosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pagePosition = args.position
        binding = FragmentSecondBinding.inflate(inflater).apply {
            viewModel = this@SecondFragment.viewModel
            lifecycleOwner = this@SecondFragment
            currItemPosition = this@SecondFragment.args.position
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    this@SecondFragment.viewModelMain.position = position
                }
            })
        }
        this@SecondFragment.viewModelMain.position = args.position
        viewModel.position = args.position
        sharedElementEnterTransition = TransitionInflater.from(this@SecondFragment.context)
            .inflateTransition(android.R.transition.move)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewInvited.observe(viewLifecycleOwner, EventObserver {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    delay(200)
                }
                if (it) startPostponedEnterTransition()
            }
        })
        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: MutableList<String>?,
                    sharedElements: MutableMap<String, View>?
                ) {
                    super.onMapSharedElements(names, sharedElements)
                    val findViewWithTag = binding.viewPager.findViewWithTag<ViewGroup>(viewModelMain.position.toString())
                    names?.get(0)?.let { sharedElements?.put(it, findViewWithTag) }
                }
            }
        )
    }
}