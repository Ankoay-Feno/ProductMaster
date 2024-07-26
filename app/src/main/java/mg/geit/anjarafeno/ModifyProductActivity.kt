package mg.geit.anjarafeno

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme

class ModifyProductActivity : ComponentActivity()
{
    private val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INVENTAIREMATSIROTheme {
                val productId = intent.getIntExtra("PRODUCT_ID", -1)
                Scaffold(modifier = Modifier)
                {
                    innerpadding ->

                ModifyProduct(
                    modifier = Modifier.padding(innerpadding).fillMaxSize().background(color = Color.Black),
                    db = db,
                    productId = productId,
                    {startActivity(Intent(this, DisplayProductActivity::class.java))}
                )
            }}
        }
    }
}

@Composable
fun ModifyProduct(modifier: Modifier, db: DBHandler, productId: Int, clickToDisplay:() -> Unit)
{
    val product = db.getProductById(productId)
    var productName by remember { mutableStateOf(product?.nameProduct ?: "") }
    var productQuantity by remember { mutableStateOf(product?.quantityProduct?.toString() ?: "") }
    var productPrice by remember { mutableStateOf(product?.priceProduct?.toString() ?: "") }

    Column(modifier = modifier.padding(top = 80.dp)) {
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (productName.isNotBlank() && productQuantity.isNotBlank() && productPrice.isNotBlank())
                {
                    val updatedProduct = Product(
                        idProduct = productId,
                        nameProduct = productName,
                        quantityProduct = productQuantity.toIntOrNull() ?: 0,
                        priceProduct = productPrice.toDoubleOrNull() ?: 0.0,
                        imageProduct = product?.imageProduct ?: ""
                    )
                    db.updateProducts(updatedProduct.idProduct,
                        updatedProduct.nameProduct.toString(),
                        updatedProduct.quantityProduct!!.toInt(),
                        updatedProduct.priceProduct!!.toDouble())
                    db.close()
                }
                clickToDisplay()
            },
            modifier = Modifier.align(Alignment.End))
        {
            Text(text = "Modifier")
        }
    }
}

