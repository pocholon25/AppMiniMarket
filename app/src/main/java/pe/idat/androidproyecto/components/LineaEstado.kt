package pe.idat.androidproyecto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun EstadoPedidoLine(estadoPedido: String) {
    // Define colores para cada estado
    val colorPendiente = Color.Red
    val colorProcesando = Color.Blue
    val colorEntregado = Color.Green

    // Define el estado actual del pedido
    val estado = when (estadoPedido) {
        "PENDIENTE" -> 1
        "PROCESANDO" -> 2
        "ENTREGADO" -> 3
        else -> 0
    }

    // Define los colores de los puntos
    val puntoPendiente = if (estado >= 1) colorPendiente else Color.Gray
    val puntoProcesando = if (estado >= 2) colorProcesando else Color.Gray
    val puntoEntregado = if (estado >= 3) colorEntregado else Color.Gray

    // Define el diseño de la línea de estado
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        EstadoPunto(color = puntoPendiente, label = "PENDIENTE")
        EstadoPunto(color = puntoProcesando, label = "PROCESANDO")
        EstadoPunto(color = puntoEntregado, label = "ENTREGADO")
    }
}

@Composable
fun EstadoPunto(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, style = TextStyle(color = Color.Black))
    }
}
