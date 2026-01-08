package pl.filipczuk.prompthub.features.auth.presentation.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import pl.filipczuk.prompthub.features.auth.presentation.components.AuthCard
import pl.filipczuk.prompthub.features.auth.presentation.viewmodel.AuthViewModel
import pl.filipczuk.prompthub.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel = remember {
        AuthViewModel(context)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthCard(
        title = "Login",
        subtitle = "Enter your details to log in to your account",
        buttonText = "Login",
        onSubmit = {
            viewModel.login(email, password) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        },
        footer = {
            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("Do not have an account? Register")
            }
        },
        content = {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("example@mail.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    )
}