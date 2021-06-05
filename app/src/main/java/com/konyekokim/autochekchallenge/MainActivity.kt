package com.konyekokim.autochekchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.konyekokim.autochekchallenge.databinding.ActivityMainBinding
import com.konyekokim.commons.extensions.isDarkTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.darkLightMode.setOnClickListener {
            val nightMode =
                if (isDarkTheme()) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }
}