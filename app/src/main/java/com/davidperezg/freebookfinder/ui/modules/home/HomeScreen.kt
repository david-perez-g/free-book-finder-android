package com.davidperezg.freebookfinder.ui.modules.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davidperezg.freebookfinder.R
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.BrowseTab
import com.davidperezg.freebookfinder.ui.modules.home.reading_list_tab.ReadingListTab
import com.davidperezg.freebookfinder.utils.Routes

const val TAB_BROWSE = 0
const val TAB_READING_LIST = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(onNavigate: (route: String) -> Unit) {
    val tab = remember { mutableStateOf(TAB_BROWSE) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { HomePageTabRow(tab = tab) }
    ) { paddingValues ->
        val tabModifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 12.dp,
                end = 12.dp
            )
            .fillMaxSize()

        when (tab.value) {
            TAB_BROWSE -> BrowseTab(tabModifier, onNavigate)
            TAB_READING_LIST -> ReadingListTab(tabModifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "FreeBookFinder",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W800,
            )
        },
    )
}

@Composable
fun HomePageTabRow(tab: MutableState<Int>) {
    val tabs = listOf(
        "Browse" to Icons.Default.Search,
        "My Reading List" to Icons.Default.CheckCircle,
    )

    TabRow(selectedTabIndex = tab.value) {
        tabs.forEachIndexed { index, (title, icon) ->
            Tab(
                text = { Text(title) },
                selected = tab.value == index,
                onClick = { tab.value = index },
                icon = {
                    Icon(imageVector = icon, contentDescription = title)
                }
            )
        }
    }
}
