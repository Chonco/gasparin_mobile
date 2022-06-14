package com.brunocarlos.inputmanagement.shared

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.children
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.providers.OfferProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

class MakeOffer : AppCompatActivity() {
    lateinit var imageView: ImageView
    private lateinit var currentPhotoPath: String
    private lateinit var cameraResultReceiver: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_offer)

        val makeOfferButton = findViewById<Button>(R.id.submit_btn)
        makeOfferButton.setOnClickListener { makeOffer() }

        cameraResultReceiver =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageUri = Uri.fromFile(File(currentPhotoPath))
                    imageView.setImageURI(imageUri)
                }
            }

        imageView = findViewById(R.id.new_offer_image)
        imageView.setOnClickListener { showDialogToSelectImageSource() }
    }

    private fun showDialogToSelectImageSource() {
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.get_picture_camera_option
                ) { _, _ -> takePhoto() }
                setNegativeButton(
                    R.string.get_picture_gallery_option,
                    DialogInterface.OnClickListener { dialogInterface, id ->

                    })
            }
            builder.setMessage("Select from where you want to get the image.")
            builder.create()
        }

        alertDialog.show()
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager).also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "com.brunocarlos.inputmanagement",
                        it,
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraResultReceiver.launch(takePictureIntent)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun makeOffer() {
        val title = findViewById<EditText>(R.id.new_offer_title).text.toString()
        val foodTypesGrid = findViewById<GridLayout>(R.id.food_types_container)
        val foodTypesList = mutableListOf<String>()
        for (child in foodTypesGrid.children) {
            val checkBox = child as CheckBox
            if (checkBox.isChecked) {
                foodTypesList.add(checkBox.text.toString())
            }
        }

        val price: Double = try {
            findViewById<EditText>(R.id.price).text.toString().toDouble()
        } catch (exception: NumberFormatException) {
            0.0
        }

        val description = findViewById<EditText>(R.id.description).text.toString()

        if (
            title.isBlank() ||
            foodTypesList.isEmpty() ||
            (price == 0.0) ||
            description.isBlank()
        ) {
            showDialogInvalidOffer()
            return
        }

        val offer = Offer(
            0,
            title,
            price,
            "Gasparin",
            foodTypesList,
            description,
            getBase64FromImageView(),
            false
        )

        OfferProvider.addOffer(offer)
        Toast.makeText(
            this,
            "Offer $title created",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun showDialogInvalidOffer() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Invalid Input")
        alertBuilder.setMessage("None input must remain empty")

        val dialog = alertBuilder.create()
        dialog.show()
    }

    private fun getBase64FromImageView(): String {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
        val byteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}