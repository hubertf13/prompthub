package pl.filipczuk.prompthub.features.post_manage.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.features.auth.presentation.viewmodel.AuthViewModel
import pl.filipczuk.prompthub.core.design_system.components.PromptHubTopBar
import pl.filipczuk.prompthub.features.post_manage.presentation.viewmodel.EditPostViewModel

@Composable
fun EditPostScreen(
    navController: NavController,
    postId: Long
) {

    val context = LocalContext.current
    val authViewModel = remember {
        AuthViewModel(context)
    }
    val editPostViewModel = remember {
        EditPostViewModel(context)
    }

    var prompt by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }

    LaunchedEffect(postId) {
        editPostViewModel.fetchPost(postId) {
            prompt = it.prompt
            tag = it.tag
        }
    }

    Scaffold(
        topBar = {
            PromptHubTopBar(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Edit Post",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF1E88E5)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Edit and share amazing prompts with the world and let your imagination run wild with any AI-powered platform.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Your AI Prompts",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        placeholder = { Text("Write your prompt here...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Tag (#product, #webdevelopment, #idea)",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(Modifier.height(4.dp))

                    OutlinedTextField(
                        value = tag,
                        onValueChange = { tag = it },
                        placeholder = { Text("#tag") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        TextButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Text("Cancel")
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (prompt.isBlank()) return@Button

                                editPostViewModel.updatePost(
                                    postId = postId,
                                    prompt = prompt,
                                    tag = tag,
                                    onSuccess = {
                                        navController.popBackStack()
                                    },
                                    onError = {
                                        // TODO Snackbar
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF6D00)
                            )
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}