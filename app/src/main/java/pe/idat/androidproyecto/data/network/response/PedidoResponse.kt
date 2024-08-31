package pe.idat.androidproyecto.data.network.response

data class PedidoResponse(
    val idPedido: Long,
    val cliente: ClienteInfoResponse,
    val venta: VentaInfoResponse,
    val fechaPedido: String,
    val estadoPedido: String
)

data class ClienteInfoResponse(
    val idcliente: Long,
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String // Aunque no es recomendable incluir contraseñas en las respuestas
)

data class VentaInfoResponse(
    val idVenta: Long,
    val cliente: ClienteInfoResponse,
    val fecha: String,
    val importe: Double,
    val detalleVenta: List<DetalleVentaInfoResponse>
)

data class DetalleVentaInfoResponse(
    val idDetalleVenta: Long,
    val producto: ProductoInfoResponse,
    val cantidad: Double,
    val subtotal: Double
)

data class ProductoInfoResponse(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val image: String
)
