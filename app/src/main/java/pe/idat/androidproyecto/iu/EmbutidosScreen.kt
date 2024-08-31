package pe.idat.androidproyecto.iu


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.components.ItemCard
import pe.idat.androidproyecto.components.LazyGrid
import pe.idat.androidproyecto.model.Compra
import pe.idat.androidproyecto.route.Rutas

@Composable
fun EmbutidosScreen(navController: NavController, viewModel: AuthViewModel) {

    val productos by viewModel.productos.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.productsByCategory("Embutidos")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 8.dp), // Padding tenue para separar de los lados de la pantalla
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "EMBUTIDOS",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), // Padding vertical para el texto
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y la lista
        LazyGrid(
            items = productos,
            columns = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp) // Padding tenue para separar de los lados de la pantalla
        ) { producto ->
            val fullimageUrl = "http://10.0.2.2:8089/img/product_img/${producto.image}"
            ItemCard(
                item = producto,
                imageRes = fullimageUrl,
                title = producto.nombre,
                description = producto.descripcion,
                price = "S/${producto.precio}",
                iconContentDescription = "Embutidos",
                onIconClick = { /* Acción al hacer clic en el icono */ },
                onAddToCartClick = {
                    viewModel.addToCart(
                        Compra(
                            nombreProducto = producto.nombre,
                            cantidad = 1,
                            precio = producto.precio,
                            imagenUrl = fullimageUrl,
                            id = producto.id.toLong()
                        )
                    )
                    navController.navigate(Rutas.Carrito.ruta)
                }
            )
        }
    }
}
