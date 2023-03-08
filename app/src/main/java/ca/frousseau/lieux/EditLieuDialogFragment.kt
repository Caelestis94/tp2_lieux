package ca.frousseau.lieux

import android.app.Dialog
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import ca.frousseau.lieux.model.Lieu
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class EditLieuDialogFragment(var lieu: Lieu?, var new: Boolean): DialogFragment() {
    var position = 0

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private var savedImage: Uri? = null




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { android.app.AlertDialog.Builder(it) }

        val inflater = requireActivity().layoutInflater

        val viewEditDialog = inflater.inflate(R.layout.fragment_edit_lieu_dialog, null)

        val buttonImage = viewEditDialog.findViewById<Button>(R.id.bt_image)
        val imagePreview = viewEditDialog.findViewById<ImageView>(R.id.image_lieu_preview)

        buttonImage.setOnClickListener {
            ChoosePicture()
        }

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imagePreview.setImageURI(uri)
                savedImage = saveImageToInternalStorage(uri)
                Toast.makeText(context, "Image enregistrée", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Aucune image sélectionnée", Toast.LENGTH_SHORT).show()
            }
        }



        if(new){
            builder?.setTitle("Ajouter un lieu")
        }else{
            builder?.setTitle("Modifier lieu ${lieu?.nom}")
            val nom = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_nom)
            val adresse = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_adresse)
            val description = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_description)
            nom.setText(lieu?.nom ?: "")
            adresse.setText(lieu?.adresse ?: "")
            description.setText(lieu?.description ?: "")
            imagePreview.setImageURI(Uri.parse(lieu?.image))


        }

        builder?.setMessage("Veuillez remplir les champs")




        builder?.setView(viewEditDialog)
            ?.setPositiveButton("Modifier") { dialog, id ->

                val nom = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_nom)
                val adresse = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_adresse)
                val description = viewEditDialog.findViewById<EditText>(R.id.edit_lieu_dialog_description)

                // fields can't be empty
                if(nom.text.isEmpty() || adresse.text.isEmpty()|| description.text.isEmpty() || savedImage == null){
                    Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if(new){
                    lieu = Lieu(nom = nom.text.toString(),adresse= adresse.text.toString(),description= description.text.toString(),image= savedImage.toString())

                }else {
                    lieu?.nom = nom.text.toString()
                    lieu?.adresse = adresse.text.toString()
                    lieu?.description = description.text.toString()
                    lieu?.image = savedImage.toString()
                }

                (activity as MainActivity).updateLieu(lieu!!, new)


            }
            ?.setNegativeButton("Annuler") { dialog, id ->
                // Send the negative button event back to the host activity
                getDialog()?.cancel()
            }
        if(builder != null) {
            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)

    }

    private fun saveImageToInternalStorage(uri: Uri): Uri? {

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("Images", ContextWrapper.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        // convert uri to bitmap
        val bitmap =
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context?.contentResolver!!, uri))
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    fun ChoosePicture(){
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }



}