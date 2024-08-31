package pe.idat.androidproyecto.viewhome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.components.ItemCard
import pe.idat.androidproyecto.components.LazyGrid
import pe.idat.androidproyecto.model.Compra
import pe.idat.androidproyecto.model.Producto
import pe.idat.androidproyecto.model.items
import pe.idat.androidproyecto.model.productos
import pe.idat.androidproyecto.model.promosList
import pe.idat.androidproyecto.route.Rutas

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Column {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)  // Espaciado entre tarjetas
                ) {
                    items(promosList) { product ->
                        ProductCardRow(imageRes = product.imagenRes)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Categorias",
                            fontSize = 16.sp,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                    TextButton(onClick = { navController.navigate(Rutas.Populares.ruta) }) {
                        Text(
                            text = "+Vendidos",
                            fontSize = 16.sp,
                            color = Color.Blue,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
        IconGrid(navController = navController)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(border = BorderStroke(2.dp, color = Color(0xFF68ECE8)))
        ) {
            VariadosScreen(navController = navController, viewModel = authViewModel)
        }
    }
}

@Composable
fun IconGrid(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .padding(horizontal = 16.dp)
            .border(border = BorderStroke(2.dp, color = Color(0xFF68ECE8))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Ajuste aquí
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in items.indices step 4) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (j in 0 until 4) {
                        if (i + j < items.size) {
                            IconItem(navController = navController, item = items[i + j])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconItem(navController: NavController, item: Pair<String, Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFF00EEE6))
                .clickable {
                    when (item.first) {
                        "Vegetales" -> navController.navigate(Rutas.Vegetales.ruta)
                        "Embutidos" -> navController.navigate(Rutas.Embutidos.ruta)
                        "Bebidas" -> navController.navigate(Rutas.Bebidas.ruta)
                        "Lacteos" -> navController.navigate(Rutas.Lacteos.ruta)
                        "Limpieza" -> navController.navigate(Rutas.Limpieza.ruta)
                        "Licores" -> navController.navigate(Rutas.Licores.ruta)
                        "Abarrotes" -> navController.navigate(Rutas.Abarrotes.ruta)
                        "Ver más" -> navController.navigate(Rutas.Seemore.ruta)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.second),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(text = item.first, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun ProductCardRow(imageRes: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))  // Agregar borde
            .padding(4.dp)  // Padding para separar borde del contenido
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFEFEFEF))  // Color de fondo para la tarjeta
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun VariadosScreen(navController: NavController, viewModel: AuthViewModel) {

    val productos by viewModel.productos.collectAsState(emptyList())

    // Cargar los productos de la categoría "Variados"
    LaunchedEffect(Unit) {
        viewModel.productsByCategory("Variados")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 8.dp), // Padding tenue para separar de los lados de la pantalla
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "VARIADOS",
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
            val fullImageUrl = "http://10.0.2.2:8089/img/product_img/${producto.image}"
            ItemCard(
                item = productos,
                imageRes = fullImageUrl,
                title = producto.nombre,
                description = producto.descripcion,
                price = "S/${producto.precio}",
                iconContentDescription = "Promoción",
                onIconClick = { /* Acción  */ },
                onAddToCartClick = {
                    viewModel.addToCart(
                        Compra(
                            nombreProducto = producto.nombre,
                            cantidad = 1,
                            precio = producto.precio,
                            imagenUrl = fullImageUrl,
                            id = producto.id.toLong()
                        )
                    )
                    navController.navigate(Rutas.Carrito.ruta)
                }
            )
        }
    }
}
