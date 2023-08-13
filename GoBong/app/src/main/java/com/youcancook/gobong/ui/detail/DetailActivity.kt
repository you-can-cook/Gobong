package com.youcancook.gobong.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.youcancook.gobong.R
import com.youcancook.gobong.adapter.RecipeListAdapter
import com.youcancook.gobong.databinding.ActivityDetailBinding
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.ui.base.GoBongActivity
import com.youcancook.gobong.ui.base.NetworkActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailActivity :
    NetworkActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
    override val viewModel: DetailViewModel by lazy {
        DetailViewModel(appContainer.goBongRepository)
    }

    private val recipeAdapter = RecipeListAdapter(onItemClick = {
        viewModel.activeRecipeStep(it)
    })
    private val reviewDialog = ReviewDialogFragment(onDismissListener = {
        viewModel.setStar(it)
    })

    private val deleteDialog: AlertDialog by lazy {
        MaterialAlertDialogBuilder(this)
            .setTitle("레시피 삭제")
            .setMessage("레시피를 삭제하면 다시 복구할 수 없습니다.\n정말 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                //TODO viewmodel 삭제
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .setCancelable(true)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            vm = viewModel
            recyclerView.adapter = recipeAdapter
        }

        binding.run {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            bookmarkImageView.setOnClickListener {
                //TODO viewmodel에서 북마크 api 보내기
                it.isSelected = it.isSelected.not()
                viewModel.bookmarkRecipe(it.isSelected)
            }

            followingButton.setOnClickListener {
                it.isSelected = it.isSelected.not()
            }

            reviewButton.setOnClickListener {
                reviewDialog.setOldStar(viewModel.getStar())
                reviewDialog.show(supportFragmentManager, null)
            }

        }

        binding.run {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.starCount.collectLatest {
                        if (it != 0) {
                            reviewButton.text = "리뷰 수정하기"
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isMine.collectLatest {
                        if (it) setDisposable()
                    }
                }
            }
        }
    }

    private fun setDisposable() {
        binding.bookmarkImageView.run {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    this@DetailActivity,
                    R.drawable.icon_delete
                )
            )
            setOnClickListener {
                deleteDialog.show()
            }
        }
    }
}