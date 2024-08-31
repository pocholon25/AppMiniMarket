package pe.idat.androidproyecto.data.network.response

data class LoginResponse(
    val message: String,
    val success: Boolean,
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String,
    val idcliente: Long
)
