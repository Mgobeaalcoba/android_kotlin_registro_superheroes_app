package com.hackaprende.registrodesuperheroes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackaprende.registrodesuperheroes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveButton.setOnClickListener {
            openDetailActivity()
        }
    }

    private fun openDetailActivity() {
        // Creamos in intent activity:
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }
}