package com.hackaprende.registrodesuperheroes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackaprende.registrodesuperheroes.DetailActivity.Companion.SUPERHERO_KEY
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

            // Instanceo un objeto de clase SuperHero para pasarlo completo en lugar de pasar cada atributo:
            val hero = SuperHero(
                superHeroName = superHeroName,
                alterEgo = alterEgo,
                bio = bio,
                power = power
            )

            // Abro la conexión con la siguiente activity para llevar los datos de esta
            openDetailActivity(hero)
        }
    }

    private fun openDetailActivity(superhero : SuperHero) {
        // Creamos in intent activity:
        val intent = Intent(this, DetailActivity::class.java)

        // Solo voy a pasar como putExtra el objeto superhero:
        intent.putExtra(SUPERHERO_KEY, superhero)

        /* Dado que vamos a pasar un objeto completo esta parte carece de sentido pero lo dejamos como alternativa para la comunicación entre activities:
        intent.putExtra(DetailActivity.SUPERHERO_NAME_KEY, superHeroName)
        intent.putExtra(DetailActivity.ALTER_EGO_KEY, alterEgo)
        intent.putExtra(DetailActivity.BIO_KEY, bio)
        intent.putExtra(DetailActivity.POWER_KEY, power)
        */

        startActivity(intent) // Con esto estamos enviando todos los put extra a DetailActivity
    }
}