package cn.changhsqinda.transition.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arcns.core.util.Event

class ViewModelFirst : ViewModel() {


    val toImageFragment = MutableLiveData<Event<Int>>()

    var testID: String = "testId"

    fun itemClick(position: Int) {
        toImageFragment.value = Event(position)
    }
}