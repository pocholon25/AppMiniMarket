package pe.idat.androidproyecto.viewlogin

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.idat.androidproyecto.AuthViewModel
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.data.network.response.ClienteRequest
import pe.idat.androidproyecto.route.Rutas
import pe.idat.androidproyecto.viewhome.RowEmail
import pe.idat.androidproyecto.viewhome.RowUsuario

@SuppressLint("UnrememberedMutableState")
@Composable
fun RegistrarScreen(navController: NavController, authViewModel: AuthViewModel) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var isValidEmail by rememberSaveable { mutableStateOf(false) }
    var isValidPassword by rememberSaveable { mutableStateOf(false) }
    var isValidConfirmPassword by rememberSaveable { mutableStateOf(false) }
    var celular by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }

    val isFormValid by derivedStateOf {
        nombre.isNotEmpty() && isValidEmail && celular.isNotEmpty() && isValidPassword && password == confirmPassword
    }

    val clienteResponse by authViewModel.clienteResponse.collectAsState()
    val registerError by authViewModel.registerError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.minimarket),
            contentDescription = "Fondo del Registro",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

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
                    containerColor = Color(0xCC333333)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    RowImage()
                    Text(
                        text = "Registrate",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White, // Ajuste de color a blanco
                        modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
                    )
                    RowUsuarioreg(
                        usuario = nombre,
                        usuarioChange = { nombre = it },
                        isValid = nombre.length >= 5,
                        label = "Nombre",
                        keyboardType = KeyboardType.Text
                    )

                    RowEmailreg(email = email, emailChange = {
                        email = it
                        isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    }, isValid = isValidEmail)

                    RowUsuarioregdos(
                        usuario = celular,
                        usuarioChange = { celular = it },
                        isValid = celular.length >= 9,
                        label = "Celular",
                        keyboardType = KeyboardType.Phone
                    )

                    RowPassword(
                        contrasena = password,
                        passwordChange = {
                            password = it
                            isValidPassword = password.length >= 5
                            passwordError =
                                if (!isValidPassword) "La contraseña debe tener al menos 5 caracteres" else null
                            confirmPasswordError =
                                if (confirmPassword.isNotEmpty() && confirmPassword != password) "Las contraseñas no coinciden" else null
                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword,
                        errorMessage = passwordError,
                        textColor = Color.White
                    )

                    RowPassword(
                        contrasena = confirmPassword,
                        passwordChange = {
                            confirmPassword = it
                            isValidConfirmPassword =
                                confirmPassword.length >= 5 && confirmPassword == password
                            confirmPasswordError =
                                if (!isValidConfirmPassword) "Las contraseñas no coinciden" else null
                        },
                        passwordVisible = confirmPasswordVisible,
                        passwordVisibleChange = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        },
                        isValidPassword = isValidConfirmPassword,
                        errorMessage = confirmPasswordError,
                        textColor = Color.White
                    )

                    RowButtonRegistrar(
                        isValidForm = isFormValid,
                        onClick = {
                            authViewModel.registerClient(
                                ClienteRequest(
                                    nombre = nombre,
                                    email = email,
                                    celular = celular,
                                    password = password
                                )
                            )
                        }
                    )

                    registerError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    clienteResponse?.let { cliente ->
                        Text(
                            text = "Registro exitoso: ${cliente.nombre}",
                            color = Color.Blue,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        LaunchedEffect(cliente) {
                            navController.navigate(Rutas.Login.ruta)
                        }
                    }

                    TextButton(
                        onClick = { navController.navigate(Rutas.Login.ruta) }
                    ) {
                        Text(
                            text = "Ya tienes una cuenta? Iniciar Sesion",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowButtonRegistrar(isValidForm: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            enabled = isValidForm,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3FFA09))
        ) {
            Text(text = "Registrar", color = Color.White)
        }
    }
}

@Composable
fun RowPassword(
    contrasena: String,
    passwordChange: (String) -> Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean,
    errorMessage: String? = null,
    textColor: Color = Color.White
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center
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
            label = { Text(text = "Password", color = textColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = passwordVisibleChange) {
                    val visibilityIcon =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    Icon(
                        visibilityIcon,
                        contentDescription = null,
                        tint = textColor
                    ) // Ajuste de color a blanco
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = colors,
            textStyle = TextStyle(color = textColor),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Key,
                    contentDescription = "Password",
                    tint = Color.White
                )
            },
            isError = !isValidPassword || errorMessage != null
        )
    }

    errorMessage?.let {
        Text(
            text = it,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
fun RowEmailreg(email: String, emailChange: (String) -> Unit, isValid: Boolean) {
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
            errorBorderColor = Color.Red,
            cursorColor = Color.White // Ajuste del color del cursor a blanco
        )

        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            label = { Text(text = "Email", color = Color.White) }, // Ajuste de color a blanco
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            textStyle = TextStyle(color = Color.White),
            isError = !isValid && email.isNotEmpty(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Correo",
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
fun RowUsuarioreg(
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
            unfocusedLabelColor = Color.White,
            focusedBorderColor = if (isValid) Color.Green else Color.Red,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            cursorColor = Color.White
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = usuarioChange,
            label = { Text(text = label, color = Color.White) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            textStyle = TextStyle(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Nombre del cliente",
                    tint = Color.White
                )
            },
            isError = !isValid && usuario.isNotEmpty()
        )
    }
}

@Composable
fun RowUsuarioregdos(
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
            unfocusedLabelColor = Color.White,
            focusedBorderColor = if (isValid) Color.Green else Color.Red,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            cursorColor = Color.White
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = usuarioChange,
            label = { Text(text = label, color = Color.White) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
            singleLine = true,
            colors = colors,
            textStyle = TextStyle(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Celular del cliente",
                    tint = Color.White
                )
            },
            isError = !isValid && usuario.isNotEmpty()
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