package com.davidperezg.freebookfinder.ui.modules.home.browse_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.davidperezg.freebookfinder.R
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.di.browseModule
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic.Book
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic.BrowseViewModel
import com.davidperezg.freebookfinder.utils.Routes
import org.koin.androidx.compose.viewModel
import org.koin.core.context.loadKoinModules

@Composable
fun BrowseTab(modifier: Modifier = Modifier, onNavigate: (route: String) -> Unit) {
    loadKoinModules(browseModule)
    val vm by viewModel<BrowseViewModel>()
    val state = vm.browseStateFlow.collectAsState()

    Column(
        modifier = modifier
    ) {
        val text = remember { mutableStateOf("") }

        SearchBar(
            text = text,
            onValueChange = { value ->
                text.value = value
                if (text.value.isNotBlank()) {
                    vm.search(text.value)
                }
            },
            onNavigate = onNavigate
        )

        if (state.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            BookList(books = state.value.books)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: MutableState<String>,
    onValueChange: (value: String) -> Unit,
    onNavigate: (route: String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text.value,
                onValueChange = onValueChange,
                label = { Text("Search") },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    onNavigate(Routes.QR_CODE_SCANNER)
                },
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_qr_code_scanner_24),
                    contentDescription = "scan QR code"
                )
            }
        }
    }
}

@Composable
fun BookList(books: List<Book>) {
    LazyColumn {
        items(books) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = book.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}