package cvb.com.br.composenote.feature_note.presentation.page_note_list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cvb.com.br.composenote.R
import cvb.com.br.composenote.core.util.DateTimeUtil
import cvb.com.br.composenote.feature_note.domain.model.Note
import cvb.com.br.composenote.feature_note.presentation.util.enum.EnumColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompNoteRow(modifier: Modifier = Modifier, note: Note, onClickEdit: (Note) -> Unit, onClickDelete: (Note) -> Unit) {
    val color = EnumColor.entries.first { enum -> enum.id == note.color }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = color.idColorResource),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = CutCornerShape(topEnd = 15.dp),
        onClick = { onClickEdit(note) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                val textColor = if (color == EnumColor.COLOR_1) {
                    Color.Black
                } else {
                    Color.White
                }

                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = note.info,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = DateTimeUtil.getDateTime(note.timestamp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                modifier = Modifier.clickable { onClickDelete(note) },
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete_note)
            )
        }
    }
}
