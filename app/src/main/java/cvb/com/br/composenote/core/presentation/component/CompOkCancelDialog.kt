package cvb.com.br.composenote.core.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun CompOkCancelDialog(
    onDismissRequest: () -> Unit,
    idOkTitle: Int,
    idCancelTitle: Int? = null,
    onOkEvent: () -> Unit,
    onCancelEvent: (() -> Unit)? = null,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onOkEvent()
                }
            ) {
                Text(stringResource(id = idOkTitle))
            }
        },

        dismissButton = {
            if (idCancelTitle != null) {
                TextButton(
                    onClick = {
                        onCancelEvent?.invoke()
                    }
                ) {
                    Text(stringResource(id = idCancelTitle))
                }
            }
        }
    )
}

