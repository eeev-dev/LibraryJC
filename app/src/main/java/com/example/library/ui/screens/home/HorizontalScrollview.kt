package com.example.library.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.R
import com.example.library.ui.theme.robotoFont

@Composable
fun HorizontalScrollView() {
    var selectedButton by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 0,
            onClick = { selectedButton = 0 },
            text = stringResource(R.string.most_viewed)
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 1,
            onClick = { selectedButton = 1 },
            text = stringResource(R.string.neaby)
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 2,
            onClick = { selectedButton = 2 },
            text = stringResource(R.string.recently)
        )
    }
}

@Composable
fun ButtonItem(
    selectedButton: Int,
    buttonIndex: Int,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedButton == buttonIndex) Color(0xFF2F2F2F) else Color.Transparent,
            contentColor = if (selectedButton == buttonIndex) Color.White else Color(0xFFC5C5C5)
        )
    ) {
        Text(
            text = text,
            fontFamily = robotoFont,
            fontSize = 14.sp
        )
    }
}