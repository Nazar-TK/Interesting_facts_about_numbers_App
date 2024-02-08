package com.example.interestinginfoaboutnumbers.presentation.numbers_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.interestinginfoaboutnumbers.domain.model.Number

@Composable
fun NumberListItem(
    number: Number,
    onItemClick: (Number) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(number) }
            .padding(0.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = number.number.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = number.info,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

}