package mg.geit.anjarafeno

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme
import java.io.File
import java.io.FileOutputStream

class AddProductActivity : ComponentActivity()
{
    val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INVENTAIREMATSIROTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                {
                    innerPadding ->
                    AddProductMain(
                        modifier = Modifier.padding(innerPadding),
                        db = db,
                        onProductAdded =
                        {
                            val intent = Intent(this, DisplayProductActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddProductMain(modifier: Modifier, db: DBHandler, onProductAdded: () -> Unit)
{
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    Row{
        Box (modifier = Modifier.padding(top = 80.dp))
        {
            SelectImage()
            { uri: Uri ->
                imageUri = uri
            }
        }
        Box {
            Column(modifier = modifier)
            {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nom du produit") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = productQuantity,
                    onValueChange = { productQuantity = it },
                    label = { Text("Quantité") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Prix") },
                    modifier = Modifier.fillMaxWidth()
                )

                //        Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick =
                    {
                        if (productName.isNotBlank() && productQuantity.isNotBlank() && productPrice.isNotBlank() && imageUri != null)
                        {
                            val imageProduct = saveImageToInternalStorage(context, imageUri!!)
                            val newProduct = Product(
                                0,
                                productName,
                                productQuantity.toInt(),
                                productPrice.toDouble(),
                                imageProduct
                            )
                            db.addNewProduct(
                                newProduct.nameProduct,
                                newProduct.quantityProduct,
                                newProduct.priceProduct,
                                imageProduct
                            )
                            //diriger vers une specifique activite
                            onProductAdded()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                )
                {
                    Text(text = "AJOUTER")
                }

            }
        }

    }
}

@Composable
fun SelectImage(onImageSelected: (Uri) -> Unit)
{
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let {
            imageUri = it
            onImageSelected(it)
        }
    }
    Column(modifier = Modifier
        .clickable { launcher.launch("image/*") }
        .border(
            BorderStroke(
                2.dp, SolidColor(
                    Color.Red
                )
            )
        )
        .size(150.dp))
    {
        Spacer(modifier = Modifier.height(10.dp))
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

fun saveImageToInternalStorage(context: Context, imageUri: Uri): String
{
    val resolver: ContentResolver = context.contentResolver
    val file = File(context.filesDir, "images")
    if (!file.exists())
    {
        file.mkdirs()
    }
    val imageFile = File(file, "${System.currentTimeMillis()}.jpg")
    try {
        resolver.openInputStream(imageUri)?.use{
            inputStream ->
            FileOutputStream(imageFile).use {
                outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0)
                {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
    } catch (e: Exception)
    {
        e.printStackTrace()
    }
    return imageFile.absolutePath
}


