package pl.filipczuk.prompthub.features.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.filipczuk.prompthub.core.design_system.theme.Amber500
import pl.filipczuk.prompthub.core.design_system.theme.Orange500
import pl.filipczuk.prompthub.core.design_system.theme.Orange600

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Discover & Share",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        val gradient = Brush.horizontalGradient(
            colors = listOf(Orange500, Orange600, Amber500)
        )

        Text(
            text = "AI-Powered",
            style = MaterialTheme.typography.displayLarge.copy(
                brush = gradient
            ),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Prompts",
            style = MaterialTheme.typography.displayLarge.copy(
                brush = gradient
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "PromptHub is an open-source AI prompting tool for modern world to discover, create and share creative prompts",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
        )
    }
}
