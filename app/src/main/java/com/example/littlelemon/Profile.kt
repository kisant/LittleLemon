package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.Constants.PREFS_USER_DATA
import com.example.littlelemon.Constants.USER_EMAIL_KEY
import com.example.littlelemon.Constants.USER_LAST_NAME_KEY
import com.example.littlelemon.Constants.USER_NAME_KEY

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val prefs by lazy { context.getSharedPreferences(PREFS_USER_DATA, Context.MODE_PRIVATE) }
    val name = prefs.getString(USER_NAME_KEY, "") ?: ""
    val lastName = prefs.getString(USER_LAST_NAME_KEY, "") ?: ""
    val email = prefs.getString(USER_EMAIL_KEY, "") ?: ""

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            TopAppBar {}
            Text(
                text = stringResource(id = R.string.info),
                Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 40.dp)
            )
            Column {
                OutlinedTextField(
                    value = name,
                    label = { Text(text = stringResource(id = R.string.user_name)) },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                OutlinedTextField(
                    value = lastName,
                    label = { Text(text = stringResource(id = R.string.user_last_name)) },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                OutlinedTextField(
                    value = email,
                    label = { Text(text = stringResource(id = R.string.user_email)) },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = {
                prefs.edit().clear().apply()
                navController.navigate(Onboarding.route) {
                    popUpTo(Profile.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp)
        ) {
            Text(text = stringResource(R.string.log_out))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {}