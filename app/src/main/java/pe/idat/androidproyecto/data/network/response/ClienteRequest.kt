package pe.idat.androidproyecto.data.network.response

data class ClienteRequest(
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String
)