package com.example.gifdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.gifdemo.databinding.ActivityTestBinding

class TestActivity : ComponentActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}