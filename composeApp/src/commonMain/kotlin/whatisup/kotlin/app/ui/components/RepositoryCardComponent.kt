package whatisup.kotlin.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkLeft
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import whatisup.kotlin.app.ui.NavigateToPullRequestsDetailScreen
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.model.Repository

/**
 * A component that displays a Repository
 */
@Composable
fun RepositoryCardComponent(
    modifier: Modifier = Modifier,
    repo: Repository,
    navigateToPullRequestsDetailScreen: NavigateToPullRequestsDetailScreen
) {
    Card(
        onClick = {
            navigateToPullRequestsDetailScreen.invoke(
                PullRequestsId(
                    repoId = repo.id,
                    owner = repo.ownerLogin,
                    repo = repo.name,
                )
            )
        },
        elevation = CardDefaults.outlinedCardElevation(),
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Row {
            Column(
                modifier = modifier.weight(5f)
            ) {
                Text(
                    text = repo.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    )
                )
                Text(
                    text = repo.description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(
                        top = 4.dp,
                        start = 16.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    )
                )
                Row {
                    Icon(
                        imageVector = Icons.Filled.ForkLeft,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 16.dp,
                            end = 0.dp,
                            bottom = 8.dp
                        )
                    )
                    Text(
                        text = repo.forksCount.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 0.dp,
                            end = 4.dp,
                            bottom = 8.dp
                        )
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 4.dp,
                            end = 0.dp,
                            bottom = 8.dp
                        )
                    )
                    Text(
                        text = repo.starsCount.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 0.dp,
                            end = 4.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }
            Column(
                modifier = modifier.weight(2f).padding(top = 16.dp)
            ) {

                val painter: Painter = rememberVectorPainter(Icons.Filled.Image)

                AsyncImage(
                    model = repo.ownerAvatar,
                    placeholder = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(72.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = repo.ownerLogin,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 4.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .align(Alignment.CenterHorizontally)

                )
            }

        }

    }

}
