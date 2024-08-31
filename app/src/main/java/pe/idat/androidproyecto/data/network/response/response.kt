package pe.idat.androidproyecto.data.network.response

data class VentaResponse(
    val idVenta: Int,
    val cliente: ClienteRespons,
    val fecha: String,
    val importe: Double,
    val detalleVenta: List<DetalleVentaResponse>,
    val estadoPedido: String
)

data class ClienteRespons(
    val idcliente: Int,
    val nombre: String,
    val email: String,
    val celular: String
)

data class DetalleVentaResponse(
    val idDetalleVenta: Int,
    val producto: ProductoResponseVenta,
    val cantidad: Int,
    val subtotal: Double
)

data class ProductoResponseVenta(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precio: Double,
    val image: String
)