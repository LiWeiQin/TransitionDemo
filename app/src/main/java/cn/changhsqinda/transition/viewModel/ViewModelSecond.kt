package cn.changhsqinda.transition.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arcns.core.util.Event

class ViewModelSecond : ViewModel() {

    var testID: String = "testId"

    var position: Int = 0

    var viewInvited = MutableLiveData<Event<Boolean>>()

}