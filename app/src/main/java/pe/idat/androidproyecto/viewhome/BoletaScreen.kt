package pe.idat.androidproyecto.viewhome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel


@Composable
fun BoletaVentaScreen(navController: NavController, authViewModel: AuthViewModel) {
    val modifier = Modifier.padding(horizontal = 16.dp)
    val ventaResponse by authViewModel.ventaResponse.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "MINIMARKET JULITA",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Nro de la Venta: ${ventaResponse?.idVenta ?: "No disponible"}",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Fecha: ${ventaResponse?.fecha ?: "No disponible"}",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Cliente: ${ventaResponse?.cliente?.nombre ?: "No disponible"}",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Email: ${ventaResponse?.cliente?.email ?: "No disponible"}",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 8.dp)
        )

        Divider(modifier = modifier.padding(vertical = 8.dp))

        Text(
            text = "Detalle de Venta:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 8.dp)
        )

        ventaResponse?.detalleVenta?.forEach { detalle ->
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = "${detalle.producto.nombre} x ${detalle.cantidad}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.weight(1f)
                )
                Text(
                    text = "S/${"%.2f".format(detalle.subtotal)}",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = modifier.height(4.dp))
        }

        Divider(modifier = modifier.padding(vertical = 8.dp))

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Importe Total:",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "S/${"%.2f".format(ventaResponse?.importe ?: 0.0)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Agregar mensaje al final
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "PUEDES CONSULTAR TU ESTADO DEL PEDIDO CON NÚMERO DE VENTA",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
