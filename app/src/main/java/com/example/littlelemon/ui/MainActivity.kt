package com.example.littlelemon.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.data.database.AppDatabase
import com.example.littlelemon.data.database.MenuEntity
import com.example.littlelemon.data.network.MenuItemNetwork
import com.example.littlelemon.data.network.MenuNetwork
import com.example.littlelemon.ui.navigation.Navigation
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.util.Constants.MENU_DATA_URI
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little_lemon_database"
        )
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuDao().isEmpty()) {
                val response = fetchMenu()
                Log.d("--->", "remote data: $response")
                cacheMenuData(response)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient
            .get(MENU_DATA_URI)
            .body<MenuNetwork>()
            .menu
    }

    private fun cacheMenuData(menuItems: List<MenuItemNetwork>) {
        if (database.menuDao().isEmpty()) {
            val menuEntityItems = menuItems.map { it.toMenuItemEntity() }
            database.menuDao().addMenuItems(*menuEntityItems.toTypedArray())
        }
    }
}

fun MenuItemNetwork.toMenuItemEntity() = MenuEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category,
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {}