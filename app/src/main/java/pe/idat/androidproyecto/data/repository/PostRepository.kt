package pe.idat.androidproyecto.data.repository

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
import pe.idat.androidproyecto.data.network.service.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService){

    suspend fun  getProductsByCategory(categoria: String) : List<ProductoResponse> {
      return postService.getProductsByCategory(categoria)
    }

    suspend fun registerClient(clienteRequest: ClienteRequest): ClienteResponse? {
        return postService.registerClient(clienteRequest)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return postService.login(loginRequest)
    }

    suspend fun updateCliente(id: Long, clienteUpdate: ClienteUpdate): ResponseMessage? {
        return postService.updateCliente(id, clienteUpdate)
    }

    suspend fun registrarVenta(ventaRequest: VentaRequest): VentaResponse? {
        return postService.registrarVenta(ventaRequest)
    }

    suspend fun obtenerPedidoPorIdVenta(idVenta: Long): PedidoResponse? {
        return postService.obtenerPedidoPorIdVenta(idVenta)
    }
}
