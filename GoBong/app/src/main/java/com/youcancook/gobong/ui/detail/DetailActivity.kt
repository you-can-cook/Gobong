package com.youcancook.gobong.ui.detail

import android.os.Bundle
import android.widget.AbsListView.OnScrollListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.databinding.ActivityDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val recipeAdapter = RecipeListAdapter(onItemClick = {
        detailViewModel.activeRecipeStep(it)
    })

    private val recyclerViewsHeight = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            vm = detailViewModel
            lifecycleOwner = this@DetailActivity
            recyclerView.adapter = recipeAdapter
        }

        var sumHeight = 0
        repeat(binding.recyclerView.childCount) {
            sumHeight += binding.recyclerView.getChildAt(it).measuredHeight
            recyclerViewsHeight[it] = sumHeight
        }

        binding.run {
            val stars = listOf(star1, star2, star3, star4, star5)

            for ((index, star) in stars.withIndex()) {
                star.setOnClickListener {
                    detailViewModel.setStar(index + 1)
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.starCount.collectLatest {
                        stars.forEach { it2 -> it2.isSelected = false }

                        for (i in 0 until it) {
                            stars[i].isSelected = true
                        }
                    }
                }

            }

            recyclerView.isNestedScrollingEnabled = true
//            nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//                val child = v.getChildAt(0)
//
//                repeat(this.recyclerView.childCount) {
//                    if (scrollY > oldScrollY) {
//                        if (scrollY >= recyclerViewsHeight[it]) {
//                            recipeAdapter.activeRecipeStep(it)
//                            return@repeat
//                        }
//                    }
//                }
//            })

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
//                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//
//
//                    recyclerView.get(0).layout
//                    layoutManager.findFirstVisibleItemPosition()
//                    val currentItem = layoutManager.findFirstVisibleItemPosition()
//                    println("currentItem $currentItem ${recyclerView.childCount}")
//                    println(
//                        "${
//                            layoutManager.scrollToPosition(
//                                1
//                            )
//                        }"
//                    )
//                    layoutManager.scrollToPosition(4)

                }
            })

        }


//

        binding.run {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.activeRecipeStep.collectLatest {

                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
//        binding.run {
//
//
//        }
    }

}