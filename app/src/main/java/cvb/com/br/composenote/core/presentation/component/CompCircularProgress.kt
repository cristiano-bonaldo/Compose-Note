package cvb.com.br.composenote.core.presentation.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CompCircularProgress() {
    CircularProgressIndicator(
        modifier = Modifier.width(46.dp),
        color = MaterialTheme.colorScheme.secondary,
    )
}