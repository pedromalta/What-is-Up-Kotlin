package whatisup.kotlin.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingRow(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
    ) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}