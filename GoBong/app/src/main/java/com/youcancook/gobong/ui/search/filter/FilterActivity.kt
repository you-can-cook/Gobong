package com.youcancook.gobong.ui.search.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youcancook.gobong.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            toolbar.setNavigationOnClickListener {
                finish()
            }

            resetButton.setOnClickListener {
                //TODO viewmodel에 reset하도록
            }

            searchButton.setOnClickListener {
                //TODO setResult(300, data)
                finish()
            }
        }

        initListeners()
    }

    private fun initListeners() {

    }
}