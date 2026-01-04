package pl.filipczuk.prompthub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import pl.filipczuk.prompthub.navigation.PromptHubNavGraph
import pl.filipczuk.prompthub.ui.theme.PromptHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PromptHubTheme {
                val navController = rememberNavController()
                PromptHubNavGraph(navController)
            }
        }
    }
}