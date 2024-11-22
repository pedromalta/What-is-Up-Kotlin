package whatisup.kotlin.app.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.model.Repo
import whatisup.kotlin.app.ui.theme.RepoAppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RepoCardComponent(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    repo: Repo,
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
        elevation =  CardDefaults.outlinedCardElevation(),
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
                    Text(
                        text = repo.forksCount.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 16.dp,
                            end = 4.dp,
                            bottom = 8.dp
                        )
                    )
                    Text(
                        text = repo.starsCount.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }
            Column(
                modifier = modifier.weight(2f)
            ) {
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
