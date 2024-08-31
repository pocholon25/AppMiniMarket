package pe.idat.androidproyecto.viewhome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.model.Compra
import pe.idat.androidproyecto.route.Rutas


@Composable
fun CarritoScreen(navController: NavController, viewModel: AuthViewModel) {
    val loginResponse by viewModel.loginResponse.collectAsState()
    val compraList by viewModel.compraList.collectAsState(emptyList())
    var total by remember { mutableDoubleStateOf(0.0) }
    val ventaResponse by viewModel.ventaResponse.collectAsState()
    LaunchedEffect(compraList) {
        total = compraList.sumOf { it.cantidad * it.precio }
    }

    LaunchedEffect(ventaResponse) {
        ventaResponse?.let {
            // Navega a la pantalla de boleta una vez que la venta se haya registrado
            navController.navigate(Rutas.Boleta.ruta)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)) {
                loginResponse?.getContentIfNotHandled()?.let{response->
                Text(text = "Hola ${response.nombre}", style = MaterialTheme.typography.titleLarge)}

                if (compraList.isEmpty()) {
                    Text(text = "El carrito está vacío", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(compraList) { compra ->
                            CompraCard(
                                compra = compra,
                                viewModel = viewModel,
                                onCantidadChange = { total = compraList.sumOf { it.cantidad * it.precio}},
                                onRemove = { viewModel.removeFromCart(compra)
                                    total = compraList.sumOf { it.cantidad * it.precio }}
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Espacio entre cada Card
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (compraList.isNotEmpty()) {
                TotalPanel(
                    total = total,
                    onComprarClick = { viewModel.registrarVenta() } // Llama a la función de registro
                )
            }
        }
    )
}

@Composable
fun CompraCard(
    compra: Compra,
    viewModel: AuthViewModel,
    onCantidadChange: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp) // Espacio alrededor de cada Card
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.teal_200),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = compra.imagenUrl,
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    placeholder = painterResource(id = R.drawable.logo),
                    contentScale = ContentScale.Crop
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = compra.nombreProducto,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Subtotal: S/${"%.2f".format(compra.cantidad * compra.precio)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        TextButton(onClick = {
                            if (compra.cantidad > 1) {
                                viewModel.updateQuantity(compra, compra.cantidad - 1)
                                onCantidadChange()
                            }
                        }) {
                            Text(text = "-")
                        }
                        Text(
                            text = "${compra.cantidad}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        TextButton(onClick = {
                            if (compra.cantidad < 10) {
                                viewModel.updateQuantity(compra, compra.cantidad + 1)
                                onCantidadChange()
                            }
                        }) {
                            Text(text = "+")
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { onRemove() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TotalPanel(total: Double, onComprarClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.teal_200),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Importe Total a Pagar",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "S/${"%.2f".format(total)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onComprarClick() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Comprar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
