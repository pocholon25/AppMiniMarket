package pe.idat.androidproyecto.viewlogin

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.idat.androidproyecto.route.Rutas
import pe.idat.androidproyecto.viewhome.RowEmail

@SuppressLint("UnrememberedMutableState")
@Composable
fun RecuperarScreen(navController: NavController){
    var email by rememberSaveable { mutableStateOf("") }
    var isValidEmail by rememberSaveable { mutableStateOf(false) }
    val isFormValid by derivedStateOf { isValidEmail }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFb5e48c))
        .padding(16.dp),
        contentAlignment = Alignment.Center
        ){
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()) {
            Card(Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
                ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    RowImage()
                    Text(
                        text = "Recuperar Contraseña",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                    )
                    RowEmail(email = email, emailChange = { email = it
                                                          isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()},
                        isValid =isValidEmail )
                    RowButtonRegistrar(isValidForm = isFormValid, text = "Validar")
                    TextButton(
                        onClick = {navController.navigate(Rutas.Registrar.ruta)},
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(text = "Crea una cuenta?")
                    }
                }
            }
        }
    }


}

@Composable
fun RowButtonRegistrar(isValidForm: Boolean, text: String) {
    TODO("Not yet implemented")
}
