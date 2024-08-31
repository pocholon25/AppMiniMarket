package pe.idat.androidproyecto.viewlogin

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.route.Rutas

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    var usuario by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isValidUsuario by rememberSaveable { mutableStateOf(false) }
    var isValidPassword by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFb5e48c))
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Card(
                Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    RowImage()
                    RowUsuario(usuario = usuario, usuarioChange = { newUsuario ->
                        usuario = newUsuario
                        isValidUsuario = newUsuario.length >= 5
                    }, isValid = isValidUsuario, label = "Usuario", keyboardType = KeyboardType.Text)
                    RowPassword(contrasena = password, passwordChange = { newPassword ->
                        password = newPassword
                        isValidPassword = newPassword.length >= 5
                    }, passwordVisible = passwordVisible, passwordVisibleChange = { passwordVisible = !passwordVisible }, isValidPassword = isValidPassword)
                    RowButtonLogin(
                        context = context,
                        authViewModel = authViewModel,
                        usuario = usuario,
                        password = password,
                        isValidEmail = isValidUsuario,
                        isValidPassword = isValidPassword,
                        navController = navController
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TextButton(
                            onClick = { navController.navigate(Rutas.Recuperar.ruta) },
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Olvidaste tu contraseña?")
                        }
                        TextButton(
                            onClick = { navController.navigate(Rutas.Registrar.ruta) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Registrate aquí")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowButtonLogin(
    context: Context,
    authViewModel: AuthViewModel,
    navController: NavController,
    usuario: String,
    password: String,
    isValidEmail: Boolean,
    isValidPassword: Boolean
) {
    val loginResponse by authViewModel.loginResponse.collectAsState()
    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                Log.d("Login", "Button clicked. Validating credentials.")
                if (isValidEmail && isValidPassword) {
                    Log.d("Login", "Credentials are valid. Initiating login.")
                    authViewModel.login(usuario, password)
                } else {
                    Log.d("Login", "Invalid credentials entered.")
                    Toast.makeText(context, "Verifica tus credenciales.", Toast.LENGTH_LONG).show()
                }
            },
            enabled = isValidEmail && isValidPassword
        ) {
            Text(text = "Iniciar Sesión")
        }
    }

    // Manejar la respuesta del login
    LaunchedEffect(loginResponse) {
        loginResponse?.getContentIfNotHandled()?.let { response ->
            Log.d("LoginResponse", "Handling response: $response")
            if (response.success) {
                Log.d("LoginResponse", "Login successful. Navigating to Home.")
                navController.navigate(Rutas.Home.ruta) {
                    popUpTo(Rutas.Login.ruta) { inclusive = true }
                }
            } else {
                Log.d("LoginResponse", "Login failed with message: ${response.message}")
                // Asegúrate de mostrar el mensaje desde la respuesta del servidor
                Toast.makeText(context, response.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
            }
        } ?: Log.d("LoginResponse", "No response or response already handled.")
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
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
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
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = passwordVisibleChange) {
                    Icon(imageVector = image, contentDescription = "Ver contraseña")
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            colors = colors
        )
    }
}

@Composable
fun RowUsuario(usuario: String, usuarioChange: (String) -> Unit, isValid: Boolean,label: String,keyboardType: KeyboardType ) {
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
            singleLine = true, colors = colors, isError = !isValid && usuario.isNotEmpty()
        )

    }

}

@Composable
fun RowImage() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id = R.drawable.minimark),
            contentDescription = "Image Login"
        )
    }

}