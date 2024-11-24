package whatisup.kotlin.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import whatisup.kotlin.app.ui.model.PullRequest
import whatisup.kotlin.app.ui.resolve
import whatisupkotlin.composeapp.generated.resources.Res
import whatisupkotlin.composeapp.generated.resources.created_at
import whatisupkotlin.composeapp.generated.resources.pr_number


/**
 * A component that displays a Pull Request
 */
@Composable
fun PullRequestCardComponent(
    modifier: Modifier,
    pullRequest: PullRequest
) {
    Column(
        modifier = modifier
    ) {

        Header(pullRequest)
        PulLRequestBodyCard(pullRequest)
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(
                    top = 4.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserNameAndAvatar(pullRequest)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth(
                    fraction = 0.8F
                )
                .height(2.dp)
                .align(Alignment.CenterHorizontally)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(modifier.height(16.dp))


    }
}

@Composable
private fun Header(
    pullRequest: PullRequest
) {
    Row(
        modifier = Modifier.padding(
            top = 8.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 0.dp
        )
    ) {
        Text(
            text = Res.string.pr_number.resolve(pullRequest.number),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                end = 8.dp,
            )
        )
        Text(
            text = Res.string.created_at.resolve(pullRequest.createAtDate),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
        )
    }

    Text(
        text = pullRequest.title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            top = 0.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 4.dp
        )
    )
}

@Composable
private fun PulLRequestBodyCard(
    pullRequest: PullRequest
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val strokeColor = MaterialTheme.colorScheme.tertiary
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 4.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 4.dp
            )
            .drawBehind {
                drawRect(
                    color = strokeColor,
                    style = stroke
                )
            },
        shape = RectangleShape,
        elevation = CardDefaults.outlinedCardElevation(),
    ) {
        Text(
            text = pullRequest.body,
            fontSize = 16.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(
                top = 4.dp,
                start = 16.dp,
                end = 8.dp,
                bottom = 4.dp
            )
        )
    }
}

@Composable
private fun UserNameAndAvatar(
    pullRequest: PullRequest
) {
    Text(
        text = pullRequest.userName,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .padding(
                end = 4.dp,
            )
    )
    val painter: Painter = rememberVectorPainter(Icons.Filled.Image)

    AsyncImage(
        model = pullRequest.userAvatar,
        placeholder = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
    )
}