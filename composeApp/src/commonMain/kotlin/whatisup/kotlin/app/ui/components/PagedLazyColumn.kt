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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import whatisup.kotlin.app.ui.defaultDebounceInMillis

@OptIn(FlowPreview::class)
@Composable
fun <T : StableId> PagedLazyColumn(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    debounceInMillis: Long = defaultDebounceInMillis(),
    items: List<T>,
    perPage: Int,
    isLoading: Boolean,
    onLoadMoreItems: () -> Unit,
    isLoadingContent: @Composable LazyListScope.() -> Unit,
    content: @Composable LazyListScope.(item: T) -> Unit
) {
    var previousItemCount by remember { mutableStateOf(perPage) }

    // Observe the scroll position
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
            .debounce(debounceInMillis)
            .collect { lastVisibleIndex ->
                val totalItems = lazyListState.layoutInfo.totalItemsCount
                if (
                    totalItems - lastVisibleIndex <= 4
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
