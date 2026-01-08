package pl.filipczuk.prompthub.core.design_system.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.features.auth.presentation.viewmodel.AuthViewModel
import pl.filipczuk.prompthub.features.auth.domain.model.AuthState
import pl.filipczuk.prompthub.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptHubTopBar(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var menuExpanded by remember { mutableStateOf(false) }
    val isLoggedIn by AuthState.isLoggedIn

    TopAppBar(
        title = {
            Text(
                text = "PromptHub",
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            if (isLoggedIn) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("My Profile") },
                        onClick = {
                            menuExpanded = false
                            navController.navigate(Screen.MyProfile.route)
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Create Prompt") },
                        onClick = {
                            menuExpanded = false
                            navController.navigate(Screen.CreatePost.route)
                        }
                    )

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Text(
                                "Log Out",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            menuExpanded = false
                            authViewModel.logout {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                }
            } else {
                TextButton(onClick = {
                    navController.navigate(Screen.Login.route)
                }) {
                    Text("Log In")
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.Register.route)
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Register")
                }
            }
        }
    )
}