package cn.changhsqinda.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import cn.changhsqinda.transition.databinding.FragmentSecondBinding
import cn.changhsqinda.transition.viewModel.ViewModelActivityMain
import cn.changhsqinda.transition.viewModel.ViewModelSecond

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val viewModel by viewModels<ViewModelSecond>()
    private lateinit var binding: FragmentSecondBinding
    private val viewModelMain by activityViewModels<ViewModelActivityMain>()
    private val args by navArgs<SecondFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater).apply {
            viewModel = this@SecondFragment.viewModel
            lifecycleOwner = this@SecondFragment
            currItemPosition = this@SecondFragment.args.position
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}