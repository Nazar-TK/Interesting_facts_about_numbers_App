package com.example.interestinginfoaboutnumbers.presentation.numbers_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = number.number.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),

        )
        Text(
            text = number.info,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
        )
    }

}