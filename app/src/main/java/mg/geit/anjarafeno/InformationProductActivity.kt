package mg.geit.anjarafeno

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme

class InformationProductActivity : ComponentActivity()
{
    var db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val productId = intent.getIntExtra("PRODUCT_ID", -1)
            Scaffold(modifier = Modifier)
            {
                innerPadding ->
                    DisplayInformationProduct(
                        modifyClick = {
                            productId ->
                            val intent = Intent(this, ModifyProductActivity::class.java)
                                .apply {
                                    putExtra("PRODUCT_ID", productId)
                                }
                            startActivity(intent)
                        },
                        deleteClick = {
                            productId ->
                            val intent = Intent(this, DeleteProductActivity::class.java)
                                .apply {
                                    putExtra("PRODUCT_ID", productId)
                                }
                            startActivity(intent)
                        },
                        db,
                        productId,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )

            }
        }

    }
}

@Composable
fun DisplayInformationProduct(modifyClick:(Int)-> Unit , deleteClick:(Int) -> Unit, db: DBHandler, productId: Int, modifier: Modifier)
{
    Box(modifier = Modifier.background(color = Color.Black))
    {
        val productState = remember { mutableStateOf<Product?>(null) }

        LaunchedEffect(productId)
        {
            productState.value = db.getProductById(productId)
        }

        productState.value?.let{
            product ->

                Column(modifier = modifier.padding(16.dp),
                    Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    )
                {
                    Image(
                        painter = rememberAsyncImagePainter(product.imageProduct),
                        contentDescription = null,
                        modifier = Modifier.size(500.dp)
                    )
                    Text(text = "Nom du produit: ${product.nameProduct}", color = Color.White)
                    Text(text = "Quantité: ${product.quantityProduct}" ,color = Color.White)
                    Text(text = "Prix: ${product.priceProduct}", color = Color.White)

                    Button(onClick = { modifyClick(product.idProduct) })
                    {
                        Text(text = "MODIFIER")
                    }
                    Button(onClick = { deleteClick(product.idProduct) })
                    {
                        Text(text = "SUPRIMER")
                    }
                }

        }

    }

}
