package mg.geit.anjarafeno

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import mg.geit.anjarafeno.ui.theme.INVENTAIREMATSIROTheme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INVENTAIREMATSIROTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                {
                    innerPadding ->
                        Main(modifier = Modifier.padding(innerPadding),
                             changeWithClick =
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
fun Main(modifier: Modifier = Modifier.background(color = Color.LightGray), changeWithClick:() -> Unit)
{

    Box (contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize())
    {
        Button(onClick = { changeWithClick() })
        {
            Text(text = "cliquer moi pour explorer")
        }
    }
}


