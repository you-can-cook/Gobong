package com.youcancook.gobong.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.youcancook.gobong.databinding.DialogLoadingBinding
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class NetworkFragment<T : ViewDataBinding, VM : NetworkViewModel>(
    @LayoutRes private val layoutRes: Int,
) : BaseFragment<T>(layoutRes) {

    abstract val viewModel: VM

    private val loadingDialog: AlertDialog by lazy {
        val dialogView = DialogLoadingBinding.inflate(requireActivity().layoutInflater)
        AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.snackBarMessage.collectLatest {
                    if (it.isNotEmpty()) {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.networkState.collectLatest {
                    when (it) {
                        NetworkState.DONE -> {
                            loadingDialog.dismiss()
                        }

                        NetworkState.LOADING -> {
                            loadingDialog.show()
                        }

                        NetworkState.SUCCESS -> {
                            loadingDialog.dismiss()
                        }

                        NetworkState.FAIL -> {
                            loadingDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

}