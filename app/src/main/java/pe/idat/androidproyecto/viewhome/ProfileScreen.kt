package pe.idat.androidproyecto.viewhome

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.components.EstadoPedidoLine
import pe.idat.androidproyecto.data.network.request.ClienteUpdate

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    LaunchedEffect(Unit) {
        authViewModel.resetPedidoState()
        authViewModel.resetUpdateMessage()
    }

    val loginResponse by authViewModel.loginResponse.collectAsState()
    val updateMessage by authViewModel.updateMessage.collectAsState()
    val estadoPedido by authViewModel.estadoPedido.observeAsState()
    val pedidoResponse by authViewModel.pedidoResponse.observeAsState()

    var idVenta by rememberSaveable { mutableStateOf("") }

    val cli = loginResponse?.peekContent()

    var nombre by rememberSaveable { mutableStateOf(cli?.nombre ?: "") }
    var email by rememberSaveable { mutableStateOf(cli?.email ?: "") }
    var celular by rememberSaveable { mutableStateOf(cli?.celular ?: "") }
    var password by rememberSaveable { mutableStateOf(cli?.password ?: "") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isNombreValid = nombre.length >= 2
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isCelularValid = celular.length >= 9
    val isPasswordValid = password.length >= 5
    val isConfirmPasswordValid = confirmPassword == password
    val isFormValid =
        isNombreValid && isEmailValid && isCelularValid && isPasswordValid && isConfirmPasswordValid

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = idVenta,
                    onValueChange = { idVenta = it },
                    label = { Text("Ingrese el número de venta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        val idVentaLong = idVenta.toLongOrNull()
                        if (idVentaLong != null) {
                            authViewModel.obtenerEstadoPedidoPorIdVenta(idVentaLong)
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Verificar")
                }
            }

            pedidoResponse?.let { pedido ->
                if (pedido.cliente.idcliente == cli?.idcliente) {
                    estadoPedido?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        EstadoPedidoLine(estadoPedido = it)
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "NO TIENE AUTORIZACIÓN",
                        color = Color.Red,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.usuario),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Perfil",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
            )
            RowUsuario(
                usuario = nombre,
                usuarioChange = { nombre = it },
                isValid = isNombreValid,
                label = "Nombre",
                keyboardType = KeyboardType.Text
            )
            RowEmail(email = email, emailChange = {
                email = it
            }, isValid = isEmailValid)
            RowUsuario(
                usuario = celular,
                usuarioChange = { celular = it },
                isValid = isCelularValid,
                label = "Celular",
                keyboardType = KeyboardType.Phone
            )
            RowPassword(
                contrasena = password,
                passwordChange = {
                    password = it
                },
                passwordVisible = passwordVisible,
                passwordVisibleChange = { passwordVisible = !passwordVisible },
                isValidPassword = isPasswordValid
            )

            RowPassword(
                contrasena = confirmPassword,
                passwordChange = {
                    confirmPassword = it
                },
                passwordVisible = confirmPasswordVisible,
                passwordVisibleChange = { confirmPasswordVisible = !confirmPasswordVisible },
                isValidPassword = isConfirmPasswordValid
            )

            RowButtonActualizar(
                isValidForm = isFormValid,
                text = "Actualizar",
                onClick = {
                    val clienteUpdate = ClienteUpdate(nombre, email, celular, password)
                    cli?.idcliente?.let { id ->
                        authViewModel.updateCliente(id, clienteUpdate)
                    }
                }
            )
            updateMessage?.let { responseMessage ->
                val color = Color.Blue
                Text(text = responseMessage.mensaje, color = color)
            }
        }
    }
}

@Composable
fun RowEmail(email: String, emailChange: (String) -> Unit, isValid: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        val colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            unfocusedLabelColor = Color(0xFF5E72AF),
            focusedBorderColor = if (isValid) Color.Green else Color.Red,
            unfocusedBorderColor = Color(0xFF5E72AF),
            errorBorderColor = Color.Red
        )

        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            isError = !isValid && email.isNotEmpty()
        )
    }
}

@Composable
fun RowUsuario(
    usuario: String,
    usuarioChange: (String) -> Unit,
    isValid: Boolean,
    label: String,
    keyboardType: KeyboardType
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        val colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            unfocusedLabelColor = Color(0xFF5E72AF),
            focusedBorderColor = if (isValid) Color.Green else Color.Red,
            unfocusedBorderColor = Color(0xFF5E72AF),
            errorBorderColor = Color.Red
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = usuarioChange,
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            isError = !isValid && usuario.isNotEmpty()
        )
    }
}

@Composable
fun RowPassword(
    contrasena: String,
    passwordChange: (String) -> Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        val colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = if (isValidPassword) Color.Green else Color.Red,
            unfocusedLabelColor = Color(0xFF5E72AF),
            focusedBorderColor = if (isValidPassword) Color.Green else Color.Red,
            unfocusedBorderColor = Color(0xFF5E72AF),
            errorBorderColor = Color.Red
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = passwordChange,
            label = { Text(text = "Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = passwordVisibleChange) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            isError = !isValidPassword && contrasena.isNotEmpty()
        )
    }
}

@Composable
fun RowButtonActualizar(isValidForm: Boolean, text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = isValidForm
        ) {
            Text(text = text)
        }
    }
}
