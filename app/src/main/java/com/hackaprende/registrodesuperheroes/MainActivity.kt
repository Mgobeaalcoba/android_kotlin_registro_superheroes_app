package com.hackaprende.registrodesuperheroes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.hackaprende.registrodesuperheroes.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    // Inicializo una lateinit var: variable no se inicializará en el momento de la declaración, sino que se inicializará más tarde:
    private lateinit var heroImage : ImageView // Al ser lateinit var puedo evitar que la misma sea nula ya que es una promesa de que luego la voy a inicializar antes de usarla.
    private var heroBitmap: Bitmap? = null
    private var picturePath = ""

    // Variable que reemplaza a la función onActivityResult por deprecada:
    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()) { // Antes usamos TakePicturePreview() que devolvía un Bitmat
        success ->
        if (success && picturePath.isNotEmpty()) {
            heroBitmap = BitmapFactory.decodeFile(picturePath)
            heroImage.setImageBitmap(heroBitmap)
        }
        /* Ya no nos sirve porque TakePicture devuelve un true en lugar de un boolean:
            bitmap ->
        heroBitmap = bitmap
        heroImage.setImageBitmap(heroBitmap)
        */
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

    private fun openCamera() {
        val file = createImageFile()

        // Si la versión del celular es inferior a Android Nogout o "N" (API 24) debemos armar el uri de una forma:
        // Si es Android Nougat o superior lo hacemos de otra forma:

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this,
                "$packageName.provider",
                file)
        } else {
            Uri.fromFile(file)
        }
        getContent.launch(uri)
        
        /* Comentamos porque "startActivityForResult tmb está deprecada:
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Implicit intent
        startActivityForResult(cameraIntent, 1000) // Es una indicación de comenzar el intent y esperar un resultado. El requestCode
        // puede ser cualquiera mientras no se repita el mismo en una misma Activity. Aca se abre la camara del telefono.
        */
    }

    private fun createImageFile(): File {
        val fileName = "superhero_image"
        val fileDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file =  File.createTempFile(fileName, ".jpg", fileDirectory)
        picturePath = file.absolutePath
        return file
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

    private fun openDetailActivity(superhero : SuperHero) {
        // Creamos in intent activity:
        val intent = Intent(this, DetailActivity::class.java) // Explicit intent

        // Solo voy a pasar como putExtra el objeto superhero:
        intent.putExtra(DetailActivity.SUPERHERO_KEY, superhero)
        intent.putExtra(DetailActivity.BITMAP_KEY, picturePath)

        /* Dado que vamos a pasar un objeto completo esta parte carece de sentido pero lo dejamos como alternativa para la comunicación entre activities:
        intent.putExtra(DetailActivity.SUPERHERO_NAME_KEY, superHeroName)
        intent.putExtra(DetailActivity.ALTER_EGO_KEY, alterEgo)
        intent.putExtra(DetailActivity.BIO_KEY, bio)
        intent.putExtra(DetailActivity.POWER_KEY, power)
        */

        startActivity(intent) // Con esto estamos enviando todos los put extra a DetailActivity
    }
}