package com.youcancook.gobong.ui.search.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FilterViewModel : ViewModel() {

    val searchInput = MutableStateFlow("")
}