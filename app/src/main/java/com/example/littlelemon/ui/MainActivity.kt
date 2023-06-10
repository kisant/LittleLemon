package com.example.littlelemon.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    private val database by lazy {
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
                val cachedMenuItems by database.menuDao().getMenuItems().observeAsState(emptyList())
                val navController = rememberNavController()
                Navigation(navController = navController)
                MenuItemList(items = cachedMenuItems)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuDao().isEmpty()) {
                val response = fetchMenu()
                cacheMenuData(response)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json")
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

@Composable
fun MenuItemList(items: List<MenuEntity>) {
    Log.d("--->", "${items.size}")
    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 20.dp)) {
        items(
            items = items,
            itemContent = {menuItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(menuItem.title)
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        textAlign = TextAlign.Right,
                        text = "%.2f".format(menuItem.price)
                    )
                }
            }
        )
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