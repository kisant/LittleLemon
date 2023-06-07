package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(isProfileIconVisible: Boolean = false, onProfileClick: () -> Unit) {
    val alphaProfile = if (isProfileIconVisible) 1F else 0F
    val actionProfile = if (isProfileIconVisible) onProfileClick else {{}}

    CenterAlignedTopAppBar(
        title = {
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxWidth(0.5F),
            )
        },
        actions = {
            IconButton(onClick = actionProfile, modifier = Modifier.alpha(alphaProfile)) {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile"
                )
            }
        }
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar {}
}