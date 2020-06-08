package cn.changhsqinda.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cn.changhsqinda.transition.databinding.FragmentFirstBinding
import cn.changhsqinda.transition.viewModel.ViewModelActivityMain
import cn.changhsqinda.transition.viewModel.ViewModelFirst
import com.arcns.core.util.EventObserver

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
        viewModel.toImageFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(it)
            )
        })
    }
}