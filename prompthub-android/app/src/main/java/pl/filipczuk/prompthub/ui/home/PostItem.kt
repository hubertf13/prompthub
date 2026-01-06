package pl.filipczuk.prompthub.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.filipczuk.prompthub.dto.PostResponse
import pl.filipczuk.prompthub.viewmodel.HomeViewModel

@Composable
fun PostItem(
    post: PostResponse,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onTagClick: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager =
        context.getSystemService(ClipboardManager::class.java)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            /* ---------- HEADER ---------- */
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "User avatar",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = post.author.username,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = post.author.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                /* ---------- COPY ICON ---------- */
                IconButton(
                    onClick = {
                        val clip = ClipData.newPlainText(
                            "AI Prompt",
                            post.prompt
                        )
                        clipboardManager?.setPrimaryClip(clip)

                        viewModel.notifyPostCopied(post.id)

                        Toast
                            .makeText(
                                context,
                                "Prompt copied to clipboard",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy prompt",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- PROMPT ---------- */
            Text(
                text = post.prompt,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- TAG ---------- */
            Text(
                text = "#${post.tag}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onTagClick(post.tag)
                }
            )
        }
    }
}