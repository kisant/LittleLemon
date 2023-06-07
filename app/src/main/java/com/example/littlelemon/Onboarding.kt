package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import com.example.littlelemon.Constants.PREFS_USER_DATA
import com.example.littlelemon.Constants.USER_EMAIL_KEY
import com.example.littlelemon.Constants.USER_LAST_NAME_KEY
import com.example.littlelemon.Constants.USER_NAME_KEY
import com.example.littlelemon.ui.theme.light_primary

@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs by lazy {
        context.getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE)
    }

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            TopAppBar {}
            Surface(color = light_primary, modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_title),
                    color = Color.White,
                    modifier = Modifier.wrapContentSize()
                )
            }
            Text(
                text = stringResource(id = R.string.info),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 32.dp, 16.dp, 0.dp)
            )
            Column(modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 32.dp)) {
                OutlinedTextField(
                    value = firstName,
                    label = { Text(text = stringResource(id = R.string.user_name)) },
                    onValueChange = { input -> firstName = input },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 16.dp)
                )
                OutlinedTextField(
                    value = lastName,
                    label = { Text(text = stringResource(id = R.string.user_last_name)) },
                    onValueChange = { input -> lastName = input },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 16.dp)
                )
                OutlinedTextField(
                    value = email,
                    label = { Text(text = stringResource(id = R.string.user_email)) },
                    onValueChange = { input -> email = input },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp),
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    Toast.makeText(
                        context,
                        getString(context, R.string.registration_error),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                    sharedPrefs.edit()
                        .putString(USER_NAME_KEY, firstName)
                        .putString(USER_LAST_NAME_KEY, lastName)
                        .putString(USER_EMAIL_KEY, email)
                        .apply()
                    Toast.makeText(
                        context,
                        getString(context, R.string.registration_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    navController.navigate(Home.route) {
                        popUpTo(Onboarding.route) { inclusive = true }
                    }
                }
            }
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}