package pe.idat.androidproyecto.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import pe.idat.androidproyecto.R

@Composable
fun BoxUse(text:String,modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.TopCenter){
        Text(text = text,fontSize = 26.sp,
            color = Color.LightGray,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun <T> LazyGrid(
    items: List<T>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    itemContent: @Composable (T) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(4.dp), // Ajustar padding entre items
        modifier = modifier
    ) {
        items(items) { item ->
            itemContent(item)
        }
    }
}

@Composable
fun <T> ItemCard(
    item: T,
    modifier: Modifier = Modifier,
    imageRes: String,
    title: String,
    description: String,
    price: String,
    iconContentDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
    onAddToCartClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF92BBBA),
                shape = RectangleShape
            ),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = price,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = iconContentDescription,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                            .clickable { onIconClick?.invoke() }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.car),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .border(2.dp, Color.Blue, shape = CircleShape)
                            .padding(3.dp)
                            .clickable { onAddToCartClick?.invoke() }
                    )
                }
            }
        }
    }
}