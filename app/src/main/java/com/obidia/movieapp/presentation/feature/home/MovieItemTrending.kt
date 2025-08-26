import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.presentation.util.MovieItem
import com.obidia.movieapp.ui.theme.robotoFamily

@Composable
fun ItemTrendingFilm(number: Int, model: ItemModel, onClick: () -> Unit) {

    Box {
        Text(
            text = if (number != 10) number.toString() else "1",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFamily,
            style = LocalTextStyle.current.merge(
                other = MaterialTheme.typography.titleMedium.copy(
                    drawStyle = Stroke(width = 6f, join = StrokeJoin.Round)
                )
            )
        )

        if (number == 10) Text(
            text = "0",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFamily,
            modifier = Modifier.padding(start = 60.dp),
            style = LocalTextStyle.current.merge(
                MaterialTheme.typography.titleMedium.copy(
                    drawStyle = Stroke(width = 6f, join = StrokeJoin.Round)
                )
            )
        )

        MovieItem(
            item = model,
            modifier = Modifier.padding(start = if (number != 10) 60.dp else 100.dp),
            onClick = {
                onClick()
            }
        )
    }
}
