import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obidia.movieapp.domain.model.ItemModel
import com.obidia.movieapp.presentation.home.MovieItem
import com.obidia.movieapp.ui.theme.poppins

@Composable
fun ItemTrendingFilm(number: Int, model: ItemModel) {

    Box {
        Text(
            text = if (number != 10) number.toString() else "1",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    drawStyle = Stroke(width = 6f, join = StrokeJoin.Round), fontFamily = poppins
                )
            )
        )

        if (number == 10) Text(
            text = "0",
            color = Color.White,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 60.dp),
            style = LocalTextStyle.current.merge(
                TextStyle(
                    drawStyle = Stroke(width = 6f, join = StrokeJoin.Round)
                )
            )
        )
        MovieItem(
            item = model,
            modifier = Modifier.padding(start = if (number != 10) 60.dp else 100.dp)
        )
    }
}
