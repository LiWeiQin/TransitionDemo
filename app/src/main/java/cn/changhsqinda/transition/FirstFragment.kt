package cn.changhsqinda.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import cn.changhsqinda.transition.databinding.FragmentFirstBinding
import cn.changhsqinda.transition.viewModel.ViewModelActivityMain
import cn.changhsqinda.transition.viewModel.ViewModelFirst
import com.arcns.core.util.EventObserver
import com.arcns.core.util.afterMeasure

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private val viewModel by viewModels<ViewModelFirst>()
    private lateinit var binding: FragmentFirstBinding
    private val viewModelMain by activityViewModels<ViewModelActivityMain>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater).apply {
            viewModel = this@FirstFragment.viewModel
            lifecycleOwner = this@FirstFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.recyclerView.afterMeasure {
            startPostponedEnterTransition()
        }
        viewModel.toImageFragment.observe(viewLifecycleOwner, EventObserver {
            val selectViewHolder = binding.recyclerView.findViewHolderForAdapterPosition(it)
                ?: return@EventObserver
            this.viewModelMain.position = it
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(it),
                FragmentNavigatorExtras(
                    selectViewHolder.itemView to it.toString()
                )
            )
        })
        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: MutableList<String>?,
                    sharedElements: MutableMap<String, View>?
                ) {
                    super.onMapSharedElements(names, sharedElements)
                    val selectViewHolder =
                        binding.recyclerView.findViewHolderForAdapterPosition(viewModelMain.position)
                            ?: return
                    names?.get(0)?.let { sharedElements?.put(it, selectViewHolder.itemView) }
                }
            }
        )
    }
}