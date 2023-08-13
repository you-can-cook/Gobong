package com.youcancook.gobong

import com.youcancook.gobong.ui.home.HomeViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeNetworkTest {
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun isEmptyList() {
        //When

        //Then
        assertEquals(0, viewModel.recipes.value.size)
    }

    @Test
    fun networkErrorTest() {


        //Then
        assertEquals("네트워크 요청에 실패했습니다", viewModel.snackBarMessage.value)
    }

    @Test
    fun followStateCorrectTest() {


    }

    @Test
    fun refreshTest() {
        //Given
        val old = viewModel.recipes.value

        //When
        //네트워크 요청

        //Then
        //기존 데이터와 개수가 동일한지 확인?
    }
}