package com.example.library.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.library.R

@Composable
fun AuthorCard(
    name: String?,
    nickname: String?,
    description: String?,
    profileUrl: String?,
    context: Context
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.profile),
                    contentDescription = stringResource(R.string.icon),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    if (name != null) {
                        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Text(text = "@$nickname", color = Color.Gray, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Профиль автора",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    // Открытие ссылки в браузере
                    if (profileUrl != null) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
        }
    }
}