package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.BookResult
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.ProjectGutenbergApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.io.IOException

val TAG = BrowseViewModel::class.simpleName

@OptIn(FlowPreview::class)
class BrowseViewModel(
    private val gutenbergApi: ProjectGutenbergApi
): ViewModel() {
    private val browseState = BrowseState()
    val browseStateFlow = MutableStateFlow(browseState)

    private val _query = MutableStateFlow("")

    private val TYPING_DELAY = 800L

    init {
        viewModelScope.launch {
            // As soon the query flow changes,
            // if the user stops typing for the set delay, the item will be emitted
            _query.debounce(TYPING_DELAY).collect { query ->
                search(query)
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search(query: String) {
        if (query.isBlank()) {
            browseStateFlow.value = browseState.copy(books = emptyList())
            return
        }

        browseStateFlow.value = browseState.copy(isLoading = true)

        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is IOException) {
                Log.e(TAG, "search: Network error", throwable)
            } else {
                Log.e(TAG, "search: Unknown error", throwable)
            }

            val snackbarInteraction = SnackbarInteraction(
                message = "An error occurred while searching"
            )

            browseStateFlow.value = browseState.copy(
                isLoading = false,
                snackbarInteraction = snackbarInteraction
            )
        }

        viewModelScope.launch(errorHandler) {
            val books = searchBooks(query)
            browseStateFlow.value = browseState.copy(
                isLoading = false,
                books = books
            )
        }
    }

    private suspend fun searchBooks(query: String): List<BookResult> {
        val response = gutenbergApi.search(query)
        return response.body()!!.results
    }
}