package mg.geit.anjarafeno

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme

class DeleteProductActivity:ComponentActivity()
{
    val db = DBHandler(this)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            INVENTAIREMATSIROTheme {
                val productId = intent.getIntExtra("PRODUCT_ID", -1)
                DeleteProduct(db, productId)
                val intent = Intent(this, DisplayProductActivity::class.java)
                startActivity(intent)
            }
        }
    }

}

@Composable
fun DeleteProduct(db:DBHandler, productId: Int)
{
    db.deleteProduct(productId)
}