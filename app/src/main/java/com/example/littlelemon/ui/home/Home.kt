package com.example.littlelemon.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.data.database.MenuEntity
import com.example.littlelemon.ui.MainActivity
import com.example.littlelemon.ui.components.TopAppBar
import com.example.littlelemon.ui.navigation.Profile
import com.example.littlelemon.ui.theme.light_onPrimary
import com.example.littlelemon.ui.theme.light_primary
import com.example.littlelemon.ui.theme.light_primaryContainer
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    val database = (LocalContext.current as MainActivity).database
    val cachedMenuItems by database.menuDao().getMenuItems().observeAsState(emptyList())
    var searchInput by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    // Get unique values from items category in database
    val filters = cachedMenuItems.map { it.category }.distinct()
    val selectedFilters = remember { mutableStateListOf<String>() }
    var isSelected by rememberSaveable { mutableStateOf(false) }


    Column {
        TopAppBar(isProfileIconVisible = true) { navController.navigate(Profile.route) }
        Surface(color = light_primary, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.hero_title),
                    style = typography.headlineLarge,
                    color = light_primaryContainer
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1F)) {
                        Text(
                            text = stringResource(R.string.hero_city),
                            style = typography.headlineSmall,
                            color = light_onPrimary
                        )
                        Text(
                            text = stringResource(R.string.hero_description),
                            style = typography.bodyMedium,
                            color = light_onPrimary,
                            modifier = Modifier.padding(top = 16.dp, end = 8.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.hero_logo),
                        contentDescription = "Hero Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    query = searchInput,
                    onQueryChange = { searchInput = it },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search icon"
                        )
                    },
                    placeholder = { Text(text = stringResource(R.string.hero_search_placeholder)) }
                ) {  }
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(filters) { filterItem ->
                isSelected = selectedFilters.contains(filterItem)
                Spacer(modifier = Modifier.padding(4.dp))
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (selectedFilters.contains(filterItem)) {
                            selectedFilters.remove(filterItem)
                        } else {
                            selectedFilters.add(filterItem)
                        }
                    },
                    label = {
                        Text(text = filterItem.replaceFirstChar { letter ->
                            if (letter.isLowerCase()) letter.titlecase(
                                Locale.ROOT
                            ) else letter.toString()
                        })
                    }
                )
            }
        }

        // Search filtering
        var filteredMenuItems = if (searchInput.isNotEmpty()) {
            cachedMenuItems.filter { searchInput in it.title }
        } else {
            cachedMenuItems
        }
        // Chip filtering
        filteredMenuItems = if (selectedFilters.isNotEmpty()) {
            filteredMenuItems.filter { it.category in selectedFilters }
        } else {
            filteredMenuItems
        }

        MenuItemList(items = filteredMenuItems)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemList(items: List<MenuEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 8.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1F)) {
                        val price = "%.2f".format(menuItem.price.toDouble())

                        Text(
                            text = menuItem.title,
                            style = typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = menuItem.description,
                            style = typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp, end = 8.dp)
                        )
                        Text(
                            text = "$$price",
                            style = typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "Dish image",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        )
    }
}