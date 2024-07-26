package com.example.allinone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allinone.ui.theme.ALLINONETheme



class InventaireActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALLINONETheme {
                val products = listOf(
                    Product("Ecran", 10.99, 10),
                    Product("UC", 30.99, 5),
                    Product("Clavier", 9.99, 3),
                    Product("USB", 3.99, 20),
                    Product("Batterie", 9.99, 50),
                    Product("Stickers", 1.0, 35),
                    Product("Télévision", 599.99, 1),
                    Product("Ordinateur portable", 899.99, 2),
                    Product("Smartphone", 699.99, 3),
                    Product("Casque sans fil", 199.99, 5),
                    Product("Tablette", 299.99, 4),
                    Product("Enceinte intelligente", 149.99, 3),
                    Product("Caméra de sécurité", 129.99, 2),
                    Product("Montre intelligente", 249.99, 3),
                    Product("Imprimante", 179.99, 2),
                    Product("Télévision", 599.99, 1),
                    Product("Ordinateur portable", 899.99, 2),
                    Product("Smartphone", 699.99, 3),
                    Product("Casque sans fil", 199.99, 5),
                    Product("Tablette", 299.99, 4),
                    Product("Enceinte intelligente", 149.99, 3),
                    Product("Caméra de sécurité", 129.99, 2),
                    Product("Montre intelligente", 249.99, 3),
                    Product("Imprimante", 179.99, 2),
                    Product("Console de jeux vidéo", 399.99, 2),
                    Product("Écouteurs Bluetooth", 79.99, 10),
                    Product("Moniteur d'ordinateur", 299.99, 3),
                    Product("Disque dur externe", 129.99, 4),
                    Product("Routeur Wi-Fi", 99.99, 3),
                    Product("Lampe LED intelligente", 49.99, 6),
                    Product("Chargeur sans fil", 29.99, 8),
                    Product("Câble HDMI", 19.99, 10),
                    Product("Souris d'ordinateur", 39.99, 5),
                    Product("Clavier sans fil", 49.99, 4),
                    Product("Batterie externe", 49.99, 5),
                    Product("Support de téléphone pour voiture", 19.99, 7),
                    Product("Hub USB", 29.99, 3),
                    Product("Adaptateur secteur USB-C", 19.99, 6),
                    Product("Microphone USB", 69.99, 2),
                    Product("Kit de nettoyage pour écrans", 14.99, 8),
                    Product("Câble de charge Lightning", 12.99, 10),
                    Product("Kit de démarrage pour maison intelligente", 299.99, 2),
                    Product("Webcam HD", 79.99, 3),
                    Product("Étui de protection pour smartphone", 24.99, 10),
                    Product("Tapis de souris", 9.99, 15),
                    Product("Stylet pour tablette", 19.99, 6),
                    Product("Haut-parleurs d'ordinateur", 79.99, 4),
                    Product("Câble Ethernet", 9.99, 10),
                    Product("Support pour ordinateur portable", 34.99, 5),
                    Product("Lecteur de cartes mémoire", 19.99, 3),
                    Product("Batteries rechargeables", 14.99, 8),
                    Product("Support mural pour télévision", 49.99, 2),
                    Product("Carte mémoire SD", 29.99, 4),
                    Product("Adaptateur Bluetooth", 19.99, 3),
                    Product("Câble d'alimentation universel", 19.99, 5),
                    Product("Câble audio RCA", 14.99, 6),
                    Product("Câble d'extension USB", 9.99, 10),
                    Product("Adaptateur HDMI vers VGA", 17.99, 4),
                    Product("Sacoche pour ordinateur portable", 39.99, 4),
                    Product("Lentille de caméra pour smartphone", 24.99, 5),
                    Product("Étui de protection pour tablette", 29.99, 6),
                    Product("Housse de protection pour casque", 19.99, 8),
                    Product("Support de bureau pour tablette", 24.99, 3),
                    Product("Souris ergonomique", 49.99, 3),
                    Product("Câble Thunderbolt", 29.99, 2),
                    Product("Ensemble de haut-parleurs Bluetooth", 99.99, 2),
                    Product("Clavier mécanique", 99.99, 2),
                    Product("Câble d'antenne TV", 14.99, 6),
                    Product("Support de casque pour gamer", 24.99, 3),
                    Product("Mini-projecteur portable", 149.99, 2)
                )
                MainScreen(products)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Nom: ${product.name}")
        Text(text = "Prix: ${product.price}")
        Text(text = "Quantite: ${product.quantity}")
        Text(text = "Valeur Total: ${product.price*product.quantity}")
    }
}

@Composable
fun ListProduct(products: List<Product>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ImportantProductInfo(products: List<Product>, modifier: Modifier = Modifier) {
    val mostExpensiveProduct = products.maxByOrNull { it.price  }
    val cheapestProduct = products.minByOrNull { it.price}

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray)
    ) {
        Text(text = "Produit Plus cher: ${mostExpensiveProduct?.name} = ${mostExpensiveProduct?.price}")
        Text(text = "Produit Bon Marche: ${cheapestProduct?.name} = ${cheapestProduct?.price}")
    }
}

@Composable
fun MainScreen(products: List<Product>) {
    Column {
        ImportantProductInfo(products, modifier = Modifier.padding(16.dp))
        ListProduct(products, modifier = Modifier.weight(1f))
    }
}

