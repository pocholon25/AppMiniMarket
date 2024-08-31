package pe.idat.androidproyecto.data.network.retrofitClient

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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitClient {

    @GET("product/productos/{categoria}")
        suspend fun getProductosByCategoria(@Path("categoria") categoria: String): Response<List<ProductoResponse>>

    @POST("clientes")
    suspend fun registrarCliente(@Body clienteRequest: ClienteRequest): Response<ClienteResponse>

    @POST("clientes/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PUT("clientes/update/{id}")
    suspend fun updateCliente(
        @Path("id") id: Long,
        @Body clienteUpdate: ClienteUpdate): Response<ResponseMessage>

    @POST("venta")
    suspend fun registrarVenta(@Body ventaRequest: VentaRequest): Response<VentaResponse>

    @GET("api/pedidos/venta/{idVenta}")
    suspend fun obtenerPedidoPorIdVenta(@Path("idVenta") idVenta: Long): PedidoResponse
    }
