package mg.geit.anjarafeno

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme

class DisplayProductActivity : ComponentActivity()
{
    val db = DBHandler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INVENTAIREMATSIROTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton =
                    {
                        FloatingActionButton(onClick =
                        {
                            val intent = Intent(this, AddProductActivity::class.java)
                            startActivity(intent)
                        },
                            containerColor = colorResource(id = R.color.teal_200))
                        {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "+")
                        }
                    })
                { innerPadding ->
                    DisplayProductScreen(db, modifier = Modifier.padding(innerPadding))
                    {
                        productId ->
                        val intent = Intent(this, InformationProductActivity::class.java).apply{
                            putExtra("PRODUCT_ID", productId)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: (Int) -> Unit)
{
    Box (modifier =  Modifier){
        Column(modifier = Modifier
            .padding(16.dp)
            .clickable { onProductClick(product.idProduct) })
        {
            Image(
                painter = rememberAsyncImagePainter(model = product.imageProduct),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .border(BorderStroke(2.dp, SolidColor(Color.Red)))
            )
            Text(text = "Nom du produit: ${product.nameProduct}")
        }
    }
}


@Composable
fun ImportantProductInfo(products: ArrayList<Product>, onProductClick: (Int) -> Unit) {
    val mostExpensiveProduct = products.maxByOrNull { it.priceProduct!!.toDouble() }
    val cheapestProduct = products.minByOrNull { it.priceProduct!!.toDouble() }

    Spacer(modifier = Modifier.padding(35.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box {
            Text(
                modifier = Modifier
                    .clickable { if (products.isNotEmpty())onProductClick(mostExpensiveProduct!!.idProduct) },
                text = "Plus cher: \n${mostExpensiveProduct?.nameProduct}  \n${mostExpensiveProduct?.priceProduct}"
            )
        }

        Box {
            Text(
                modifier = Modifier
                    .clickable { if (products.isNotEmpty())onProductClick(cheapestProduct!!.idProduct) },
                text = "Bon Marché:\n${cheapestProduct?.nameProduct}  \n${cheapestProduct?.priceProduct}"
            )
        }
    }
}


@Composable
fun DisplayProductScreen(db: DBHandler, modifier: Modifier, onProductClick: (Int) -> Unit)
{
    val productList = db.readProducts()

        Column {
            ImportantProductInfo(products = db.readProducts(), onProductClick = onProductClick)
            LazyColumn(modifier = Modifier)
            {
                items(productList)
                { product ->
                    ProductItem(product = product, onProductClick = onProductClick)
                }
            }
        }

}
