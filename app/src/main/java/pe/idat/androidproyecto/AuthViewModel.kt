package pe.idat.androidproyecto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pe.idat.androidproyecto.core.util.Evento
import pe.idat.androidproyecto.data.network.request.Cliente
import pe.idat.androidproyecto.data.network.request.ClienteResponse
import pe.idat.androidproyecto.data.network.request.ClienteUpdate
import pe.idat.androidproyecto.data.network.request.DetalleVenta
import pe.idat.androidproyecto.data.network.request.LoginRequest
import pe.idat.androidproyecto.data.network.request.Producto
import pe.idat.androidproyecto.data.network.request.VentaRequest
import pe.idat.androidproyecto.data.network.response.ClienteRequest
import pe.idat.androidproyecto.data.network.response.LoginResponse
import pe.idat.androidproyecto.data.network.response.PedidoResponse
import pe.idat.androidproyecto.data.network.response.ProductoResponse
import pe.idat.androidproyecto.data.network.response.ResponseMessage
import pe.idat.androidproyecto.data.network.response.VentaResponse
import pe.idat.androidproyecto.domain.GetPostUseCase
import pe.idat.androidproyecto.model.Compra
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {

    private val _pedidoResponse = MutableLiveData<PedidoResponse?>()
    val pedidoResponse: LiveData<PedidoResponse?> get() = _pedidoResponse

    private val _estadoPedido = MutableLiveData<String?>()
    val estadoPedido: LiveData<String?> get() = _estadoPedido

    private val _clienteActual = MutableLiveData<Cliente?>()
    val clienteActual: LiveData<Cliente?> get() = _clienteActual

    private val _compraList = MutableStateFlow<List<Compra>>(emptyList())
    val compraList: StateFlow<List<Compra>> get() = _compraList


    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> get() = _usuario


    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password


    private val _productos = MutableStateFlow<List<ProductoResponse>>(emptyList())
    val productos: StateFlow<List<ProductoResponse>> get() = _productos


    private val _clienteResponse = MutableStateFlow<ClienteResponse?>(null)
    val clienteResponse: StateFlow<ClienteResponse?> get() = _clienteResponse

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError: StateFlow<String?> get() = _registerError

    private val _loginResponse = MutableStateFlow<Evento<LoginResponse>?>(null)
    val loginResponse: StateFlow<Evento<LoginResponse>?> get() = _loginResponse

    private val _updateMessage = MutableStateFlow<ResponseMessage?>(null)
    val updateMessage: StateFlow<ResponseMessage?> get() = _updateMessage

    private val _ventaResponse = MutableStateFlow<VentaResponse?>(null)
    val ventaResponse: StateFlow<VentaResponse?> get() = _ventaResponse

    private val _ventaError = MutableStateFlow<String?>(null)
    val ventaError: StateFlow<String?> get() = _ventaError

    fun logout() {
        viewModelScope.launch {
            try {
                _loginResponse.value = null
                _compraList.value = emptyList()
                _clienteResponse.value = null
                _productos.value = emptyList()
                _ventaResponse.value = null
                _usuario.value = ""
                _password.value = ""

                Log.d("AuthViewModel", "Logout successful")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error during logout: ${e.message}")
                e.printStackTrace()
            }
        }
    }


    fun productsByCategory(categoria: String) {
        viewModelScope.launch {
            try {
                val products = getPostUseCase(categoria)
                _productos.value = products
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun registerClient(clienteRequest: ClienteRequest) {
        viewModelScope.launch {
            try {
                val response = getPostUseCase(clienteRequest)
                if (response != null) {
                    _clienteResponse.value = response
                    _registerError.value = null
                } else {
                    _registerError.value = "Error en el registro"
                }
            } catch (e: Exception) {
                _registerError.value = "Excepción: ${e.message}"
                e.printStackTrace()
            }
        }
    }


    fun login(usuario: String, password: String) {
        viewModelScope.launch {
            Log.d("AuthViewModel", "Iniciando el proceso de login")
            try {
                Log.d("AuthViewModel", "Enviando solicitud de login: usuario=$usuario, password=$password")
                val response = getPostUseCase.login(LoginRequest(usuario, password))

                Log.d("AuthViewModel", "Verificando respuesta del servidor...")
                if (response != null) {
                    Log.d("AuthViewModel", "Respuesta del servidor recibida: $response")
                    _loginResponse.value = Evento(response)
                    if (response.success) {
                        Log.d("AuthViewModel", "Login exitoso, usuario autenticado.")
                        _clienteActual.value = Cliente(idcliente = response.idcliente)
                    } else {
                        Log.e("AuthViewModel", "Login fallido: ${response.message}")
                    }
                } else {
                    Log.e("AuthViewModel", "Respuesta nula del servidor.")
                    _loginResponse.value = Evento(
                        LoginResponse(
                            message = "No se pudo obtener una respuesta válida del servidor.",
                            success = false,
                            nombre = "",
                            email = "",
                            celular = "",
                            password = "",
                            idcliente = 0
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error de red o servidor: ${e.message}", e)
                _loginResponse.value = Evento(
                    LoginResponse(
                        message = e.message ?: "Error inesperado",
                        success = false,
                        nombre = "",
                        email = "",
                        celular = "",
                        password = "",
                        idcliente = 0
                    )
                )
            }
            Log.d("AuthViewModel", "Proceso de login finalizado")
        }
    }
    fun updateCliente(id: Long, clienteUpdate: ClienteUpdate) {
        viewModelScope.launch {
            try {
                val response = getPostUseCase.updateCliente(id, clienteUpdate)
                if (response != null) {
                    _updateMessage.value = ResponseMessage(mensaje = response.mensaje)
                } else {
                    _updateMessage.value =
                        ResponseMessage(mensaje = "Error al actualizar el perfil")
                }
            } catch (e: Exception) {
                _updateMessage.value = ResponseMessage(mensaje = "Excepción: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun registrarVenta() {
        viewModelScope.launch {
            try {
                val cliente = _clienteActual.value
                if (cliente == null) {
                    _ventaError.value =
                        "No se pudo crear la solicitud de venta: Cliente no logueado."
                    return@launch
                }

                val ventaRequest = createVentaRequest(cliente)
                val response = getPostUseCase(ventaRequest)
                if (response != null) {
                    _ventaResponse.value = response
                    _ventaError.value = null
                    Log.d("VentaResponse", "Venta registrada exitosamente: ${response.toString()}")

                    // Verifica si la venta pertenece al cliente actual
                    if (response.cliente.idcliente.toLong() == cliente.idcliente.toLong()) { // Conversión de Int a Long
                        val estadoPedido = response.estadoPedido
                        _estadoPedido.value = estadoPedido
                    } else {
                        _estadoPedido.value = null // Si el id no coincide, el estado se vuelve null
                    }
                } else {
                    _ventaError.value = "Error al registrar la venta"
                }
            } catch (e: Exception) {
                _ventaError.value = "Excepción: ${e.message}"
                e.printStackTrace()
            }
        }
    }


    fun createVentaRequest(cliente: Cliente): VentaRequest {
        val fecha = getCurrentDate()
        val detalleVenta = _compraList.value.map { compra ->
            DetalleVenta(
                producto = Producto(id = compra.id),
                cantidad = compra.cantidad
            )
        }

        return VentaRequest(
            cliente = cliente,
            fecha = fecha,
            detalleVenta = detalleVenta
        )
    }

    fun obtenerEstadoPedidoPorIdVenta(idVenta: Long) {
        viewModelScope.launch {
            try {
                println("Solicitando estado del pedido para la venta con ID: $idVenta")
                val response = getPostUseCase.getPedidoPorIdVenta(idVenta)
                if (response != null) {
                    println("Estado del pedido recibido: ${response.estadoPedido}")
                    _estadoPedido.value = response.estadoPedido
                    _pedidoResponse.value = response // Almacenar el PedidoResponse completo
                } else {
                    println("No se encontró el pedido")
                    _estadoPedido.value = "No se encontró el pedido"
                    _pedidoResponse.value = null
                }
            } catch (e: Exception) {
                println("Error al obtener el estado del pedido: ${e.message}")
                _estadoPedido.value = "Error al obtener el estado del pedido"
                _pedidoResponse.value = null
                e.printStackTrace()
            }
        }
    }


    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date())
    }


    fun addToCart(compra: Compra) {
        _compraList.value = _compraList.value + compra
        println("Producto añadido al carrito: ${compra.nombreProducto}")
        println("Carrito actual: ${_compraList.value}")
    }

    fun removeAllFromCart() {
        _compraList.value = emptyList()
    }

    fun removeFromCart(compra: Compra) {
        _compraList.value = _compraList.value - compra
    }

    fun updateQuantity(compra: Compra, newQuantity: Int) {
        _compraList.value = _compraList.value.map {
            if (it == compra) it.copy(cantidad = newQuantity) else it
        }
    }

    fun resetPedidoState() {
        _estadoPedido.value = null
        _pedidoResponse.value = null
    }

    fun resetUpdateMessage() {
        _updateMessage.value = null
    }

    fun getTotal(): Double {
        return _compraList.value.sumOf { it.cantidad * it.precio }
    }

    val total: StateFlow<Double> = _compraList.map { compraList ->
        compraList.sumOf { it.cantidad * it.precio }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

}

