package pe.idat.androidproyecto.viewlogin


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.route.Rutas
import pe.idat.androidproyecto.viewhome.RowEmail

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isValidEmail by rememberSaveable { mutableStateOf(false) }
    var isValidPassword by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val loginResponse by authViewModel.loginResponse.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.loguito),
            contentDescription = "Fondo del Login",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Ajuste para posicionar el contenido al fondo
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Card(
                Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xCC333333) // Fondo semitransparente
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    RowImage()
                    RowEmaillog(
                        email = email,
                        emailChange = { newEmail ->
                            email = newEmail
                            isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
                            authViewModel.updateUsuario(newEmail)
                        },
                        isValid = isValidEmail
                    )

                    RowPassword(
                        contrasena = password,
                        passwordChange = { newPassword ->
                            password = newPassword
                            isValidPassword = newPassword.length >= 5
                            authViewModel.updatePassword(newPassword)
                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword
                    )

                    RowButtonLogin(
                        authViewModel = authViewModel,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword,
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
                            Text(text = "Olvidaste tu contraseña?", color = Color.White) // Color ajustado a blanco
                        }
                        TextButton(
                            onClick = { navController.navigate(Rutas.Registrar.ruta) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Registrate aquí", color = Color.White) // Color ajustado a blanco
                        }
                    }
                }
            }
        }
    }

    // Manejar la respuesta del login
    loginResponse?.getContentIfNotHandled()?.let { response ->
        if (response.success) {
            navController.navigate(Rutas.Home.ruta)
        } else {
            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun RowEmaillog(email: String, emailChange: (String) -> Unit, isValid: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        val colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            unfocusedLabelColor = Color.White,
            focusedBorderColor = if (isValid) Color.Green else Color.Red,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red
        )

        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            label = { Text(text = "Email", color = Color.White) }, // Color del label ajustado
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White), // Ajuste del color del texto ingresado
            isError = !isValid && email.isNotEmpty()
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
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = if (isValidPassword) Color.Green else Color.Red,
            unfocusedLabelColor = Color.White,
            focusedBorderColor = if (isValidPassword) Color.Green else Color.Red,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña", color = Color.White) }, // Color del label ajustado
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = passwordVisibleChange) {
                    Icon(imageVector = image, contentDescription = "Ver contraseña", tint = Color.White) // Icono ajustado
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            colors = colors,
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White) // Ajuste del color del texto ingresado
        )
    }
}
@Composable
fun RowButtonLogin(
    authViewModel: AuthViewModel,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (isValidEmail && isValidPassword) {
                    authViewModel.login()
                }
            },
            enabled = isValidEmail && isValidPassword,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3FFA09))
        ) {
            Text(text = "Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

