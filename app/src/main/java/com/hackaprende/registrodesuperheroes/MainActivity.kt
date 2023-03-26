package com.hackaprende.registrodesuperheroes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.hackaprende.registrodesuperheroes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Inicializo una lateinit var: variable no se inicializará en el momento de la declaración, sino que se inicializará más tarde:
    private lateinit var heroImage : ImageView // Al ser lateinit var puedo evitar que la misma sea nula ya que es una promesa de que luego la voy a inicializar antes de usarla.
    private var heroBitmap: Bitmap? = null

    // Variable que reemplaza a la función onActivityResult por deprecada:
    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            bitmap ->
        heroBitmap = bitmap
        heroImage.setImageBitmap(heroBitmap)
    }

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

        // Inicializo a mi "lateinit var":
        heroImage = binding.superheroImage
        heroImage.setOnClickListener {
            openCamera()
        }

    }

    private fun openDetailActivity(superhero : SuperHero) {
        // Creamos in intent activity:
        val intent = Intent(this, DetailActivity::class.java) // Explicit intent

        // Solo voy a pasar como putExtra el objeto superhero:
        intent.putExtra(DetailActivity.SUPERHERO_KEY, superhero)
        intent.putExtra(DetailActivity.BITMAP_KEY, heroImage.drawable.toBitmap())

        /* Dado que vamos a pasar un objeto completo esta parte carece de sentido pero lo dejamos como alternativa para la comunicación entre activities:
        intent.putExtra(DetailActivity.SUPERHERO_NAME_KEY, superHeroName)
        intent.putExtra(DetailActivity.ALTER_EGO_KEY, alterEgo)
        intent.putExtra(DetailActivity.BIO_KEY, bio)
        intent.putExtra(DetailActivity.POWER_KEY, power)
        */

        startActivity(intent) // Con esto estamos enviando todos los put extra a DetailActivity
    }


    private fun openCamera() {
        getContent.launch(null)
        /* Comentamos porque "startActivityForResult tmb está deprecada:

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Implicit intent
        startActivityForResult(cameraIntent, 1000) // Es una indicación de comenzar el intent y esperar un resultado. El requestCode
        // puede ser cualquiera mientras no se repita el mismo en una misma Activity. Aca se abre la camara del telefono.

        */
    }

    /* Comentamos esta parte dado que onActivityResult ya está deprecado y no tiene mantenimiento:

     // función que se va a llamar automaticamente cuando volvamos de tomar una foto:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            val extras = data?.extras // Si la camara falla puedo no devolver bien las imagenes. Por ello el operador "?"
            val heroBitmap = extras?.getParcelable<Bitmap>("data")
            heroImage.setImageBitmap(heroBitmap)
        }
    }

     */
}