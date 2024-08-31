package pe.idat.androidproyecto.domain

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
import pe.idat.androidproyecto.data.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val postRepository: PostRepository){
    suspend operator fun invoke(categoria: String): List<ProductoResponse> {
        return postRepository.getProductsByCategory(categoria)
    }

    suspend operator fun invoke(clienteRequest: ClienteRequest): ClienteResponse? {
        return postRepository.registerClient(clienteRequest)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return postRepository.login(loginRequest)
    }

    suspend fun updateCliente(id: Long, clienteUpdate: ClienteUpdate): ResponseMessage? {
        return postRepository.updateCliente(id, clienteUpdate)
    }

    suspend operator fun invoke(ventaRequest: VentaRequest): VentaResponse? {
        return postRepository.registrarVenta(ventaRequest)
    }

    suspend fun getPedidoPorIdVenta(idVenta: Long): PedidoResponse? {
        return postRepository.obtenerPedidoPorIdVenta(idVenta)
    }
}