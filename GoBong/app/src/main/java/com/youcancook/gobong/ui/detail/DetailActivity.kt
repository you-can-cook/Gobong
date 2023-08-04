package com.youcancook.gobong.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youcancook.gobong.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}