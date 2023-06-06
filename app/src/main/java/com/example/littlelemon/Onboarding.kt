package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.light_primary

@Composable
fun Onboarding() {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(90.dp)
                .padding(52.dp, 27.dp, 52.dp, 27.dp)
        )
        Surface(
            color = light_primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Text(
                text = "Let's get to know you",
                color = Color.White,
                modifier = Modifier.wrapContentSize()
            )
        }
        Text(
            text = "Personal information",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp, 32.dp, 16.dp, 0.dp)
        )
        Column(modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 32.dp)) {
            OutlinedTextField(
                value = firstName,
                label = { Text(text = "First name") },
                onValueChange = { input -> firstName = input },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            OutlinedTextField(
                value = lastName,
                label = { Text(text = "Last name") },
                onValueChange = { input -> lastName = input },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            OutlinedTextField(
                value = email,
                label = { Text(text = "Email") },
                onValueChange = { input -> email = input },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 16.dp, 32.dp),
            onClick = { }
        ) {
            Text(text = "Register")
        }
    }
}