package com.youcancook.gobong

import com.youcancook.gobong.di.GoBongContainer
import com.youcancook.gobong.fake.FakeGoBongContainer
import com.youcancook.gobong.ui.addRecipe.AddRecipeViewModel
import com.youcancook.gobong.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before

class AddRecipeTest {

    private lateinit var viewModel: AddRecipeViewModel
    private lateinit var fakeViewModel: AddRecipeViewModel
    private val appContainer = GoBongContainer()
    private val fakeContainer = FakeGoBongContainer()

    @Before
    fun setUp() {
        viewModel = AddRecipeViewModel(appContainer.goBongRepository)
        fakeViewModel = AddRecipeViewModel(fakeContainer.goBongRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


}