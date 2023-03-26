package com.hackaprende.registrodesuperheroes

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackaprende.registrodesuperheroes.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    /*
    La principal utilidad del companion object es proporcionar un espacio de nombres para miembros de clase que deben
    ser accesibles desde fuera de la clase sin necesidad de una instancia de la misma. Los miembros del companion object
    se pueden acceder mediante el nombre de la clase, seguido de un punto y el nombre del miembro.
    Poniendo las keys acá y en la otra actividad con la que está vinculada esta activity evitamos posibles typos.
    */
    companion object {

        // Establezco la key del objeto completo que voy a pasar y la key del Bitmap:
        const val SUPERHERO_KEY = "superhero"
        const val BITMAP_KEY = "bitmat"

        /* Dado que estamos pasando el objeto completo carece de sentido tener todas estas keys en el companion object:
        const val SUPERHERO_NAME_KEY = "superhero_name"
        const val ALTER_EGO_KEY = "alter_ego"
        const val BIO_KEY = "bio"
        const val POWER_KEY = "power"
        */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Levantamos todo lo que nos traemos de MainActivity:
        val bundle = intent.extras!! // Estos son los extras que pasamos desde MainActivity. Le especifico que no pueden venir nulos con !!

        // Recupero el objeto y el bitmap en nuevas variable:
        val superhero = bundle.getParcelable<SuperHero>(SUPERHERO_KEY)!! // Advertimos que no puede devolver null
        val bitmap = bundle.getParcelable<Bitmap>(BITMAP_KEY)!!

        /* Ahora que recibimos el objeto parcelable entero ya no necesitamos esto sino recuperar el objeto completo en una nueva variable:
        val superHeroName = bundle.getString(SUPERHERO_NAME_KEY) ?: "" // Elvis operator: En caso de que vengan nulos lo transformo en cadenas vacias.
        val alterEgo = bundle.getString(ALTER_EGO_KEY) ?: ""
        val bio = bundle.getString(BIO_KEY) ?: ""
        val power = bundle.getFloat(POWER_KEY)
        */

        /* Ya podemos quitar estas lineas donde mostramos el contenido de nuestro objeto en el layout dado que ya cargamos
        la variable superhero en la "data" del data binding del layout:

        Obtenidos los valores que traigo de MainActivity los voy a mostrar en esta nueva activity:

        binding.heroNameText.text = superhero.superHeroName
        binding.alterEgoText.text = superhero.alterEgo
        binding.bioText.text = superhero.bio
        binding.powerBar.rating = superhero.power

        */

        // Especificamos q el superhero que levantamos es el objeto que queremos mostrar de
        // clase SuperHero en nuestro data binding:

        binding.heroImage.setImageBitmap(bitmap)
        binding.superhero = superhero
    }
}