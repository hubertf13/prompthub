package pl.filipczuk.prompthub.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptHubTopBar(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "PromptHub",
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            TextButton(onClick = onLoginClick) {
                Text("Log In")
            }
            Button(
                onClick = onRegisterClick,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Register")
            }
        }
    )
}