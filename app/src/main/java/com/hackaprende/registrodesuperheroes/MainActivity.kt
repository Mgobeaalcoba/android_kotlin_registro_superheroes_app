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
            // Registro la información que el usuario ingreso en mi app:
            val superHeroName = binding.superheroNameEdit.text.toString()
            val alterEgo = binding.alterEgoEdit.text.toString()
            val bio = binding.bioEdit.text.toString()
            val power = binding.powerBar.rating
            // Todos estos los voy a enviar a openDetailActivity

            // Abro la conexión con la siguiente activity para llevar los datos de esta
            openDetailActivity(superHeroName, alterEgo, bio, power)
        }
    }

    private fun openDetailActivity(superHeroName: String, alterEgo: String, bio: String, power: Float) {
        // Creamos in intent activity:
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.SUPERHERO_NAME_KEY, superHeroName)
        intent.putExtra(DetailActivity.ALTER_EGO_KEY, alterEgo)
        intent.putExtra(DetailActivity.BIO_KEY, bio)
        intent.putExtra(DetailActivity.POWER_KEY, power)
        startActivity(intent) // Con esto estamos enviando todos los put extra a DetailActivity
    }
}