package pe.idat.androidproyecto.data.network.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.idat.androidproyecto.data.network.request.ClienteResponse
import pe.idat.androidproyecto.data.network.request.ClienteUpdate
import pe.idat.androidproyecto.data.network.request.LoginRequest
import pe.idat.androidproyecto.data.network.request.VentaRequest
import pe.idat.androidproyecto.data.network.response.ClienteRequest
import pe.idat.androidproyecto.data.network.response.LoginResponse
import pe.idat.androidproyecto.data.network.response.PedidoResponse
import pe.idat.androidproyecto.data.network.response.ProductoResponse
import pe.idat.androidproyecto.data.network.response.ResponseMessage
import pe.idat.androidproyecto.data.network.response.VentaResponse
import pe.idat.androidproyecto.data.network.retrofitClient.RetrofitClient
import javax.inject.Inject

class PostService @Inject constructor(private val retrofitClient: RetrofitClient) {

    suspend fun getProductsByCategory(categoria: String): List<ProductoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofitClient.getProductosByCategoria(categoria)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("PostService", "Error al obtener productos por categoría:${e.message}")
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun registerClient(clienteRequest: ClienteRequest): ClienteResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("PostService", "Enviando solicitud para registrar cliente: $clienteRequest")
                val response = retrofitClient.registrarCliente(clienteRequest)
                Log.d("PostService", "Respuesta de registro: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    response.body()
                } else {
                    Log.e("PostService", "Error en la respuesta: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PostService", "Error al registrar cliente: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("PostService", "Enviando solicitud de login: $loginRequest")
                val response = retrofitClient.login(loginRequest)
                Log.d("PostService", "Respuesta de login: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    response.body()
                } else {
                    Log.e("PostService", "Error en la respuesta de login: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PostService", "Error al realizar login: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }


    suspend fun updateCliente(id: Long, clienteUpdate: ClienteUpdate): ResponseMessage? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("PostService", "Enviando solicitud para actualizar cliente: $clienteUpdate")
                val response = retrofitClient.updateCliente(id, clienteUpdate)
                Log.d("PostService", "Respuesta de actualización: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    response.body()
                } else {
                    Log.e("PostService", "Error en la respuesta de actualización: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PostService", "Error al actualizar cliente: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun registrarVenta(ventaRequest: VentaRequest): VentaResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("PostService", "Enviando solicitud para registrar venta: $ventaRequest")
                val response = retrofitClient.registrarVenta(ventaRequest)
                Log.d("PostService", "Respuesta de registro de venta: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    response.body()
                } else {
                    Log.e("PostService", "Error en la respuesta de registro de venta: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("PostService", "Error al registrar venta: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
    suspend fun obtenerPedidoPorIdVenta(idVenta: Long): PedidoResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("PostService", "Enviando solicitud para obtener pedido por idVenta: $idVenta")
                val response = retrofitClient.obtenerPedidoPorIdVenta(idVenta)
                Log.d("PostService", "Respuesta de pedido: ${response.toString()}")
                response
            } catch (e: Exception) {
                Log.e("PostService", "Error al obtener el pedido por idVenta: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

}


