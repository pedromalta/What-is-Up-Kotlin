package whatisup.kotlin.app.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import whatisup.kotlin.app.ui.model.StableId

/**
 * A Lazy Column that is scrollable and fetch new data when needed
 * you can define a [onLoadMoreItems] lambda to load more data
 * you can define a control [isLoading] state when [onLoadMoreItems] is running
 * and provide a [isLoadingContent] composable to be shown
 * [content] will be the composable for each item
 */
@Composable
fun <T : StableId> PagedLazyColumn(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    items: List<T>,
    perPage: Int,
    isLoading: Boolean,
    onLoadMoreItems: () -> Unit,
    isLoadingContent: @Composable LazyListScope.() -> Unit,
    content: @Composable LazyListScope.(item: T) -> Unit
) {
    var previousItemCount by remember { mutableStateOf(perPage) }

    LaunchedEffect(lazyListState) {
        // Observe the scroll position
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
            .collect { lastVisibleIndex ->
                val totalItems = lazyListState.layoutInfo.totalItemsCount
                if (
                //Fetch new items when there's less than 6 to the end of the scroll
                    totalItems - lastVisibleIndex < 6
                    && totalItems >= previousItemCount
                ) {
                    previousItemCount = totalItems
                    onLoadMoreItems()
                }
            }
    }

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        items(
            items = items,
            key = { it.id }
        ) {
            this@LazyColumn.content(it)
        }

        if (isLoading)
            item {
                this@LazyColumn.isLoadingContent()
            }
    }
}
