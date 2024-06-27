package com.davidperezg.freebookfinder.ui.modules.home.browse_tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.Author
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.BookResult

@Composable
fun BookSearchResults(books: List<BookResult>) {
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
                    Text(text = formatAuthors(book.agents) , style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

fun formatAuthors(authors: List<Author>): String {
    var s = ""

    for (author in authors) {
        s += author.person + ", "
    }

    return s.removeSuffix(", ")
}