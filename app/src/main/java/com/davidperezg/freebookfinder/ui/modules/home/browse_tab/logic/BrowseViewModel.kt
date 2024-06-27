package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

data class Book(val title: String, val description: String)

val TAG = BrowseViewModel::class.simpleName

@OptIn(FlowPreview::class)
class BrowseViewModel: ViewModel() {
    private val browseState = BrowseState()
    val browseStateFlow = MutableStateFlow(browseState)

    private val _query = MutableStateFlow("")

    private val TYPING_DELAY = 500L

    init {
        viewModelScope.launch {
            // As soon the query flow changes,
            // if the user stops typing for the set delay, the item will be emitted
            _query.debounce(TYPING_DELAY).collect { query ->
                if (query.isBlank()) return@collect
                search(query)
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search(query: String) {
        viewModelScope.launch {
            Log.i(TAG, "search: triggered with $query")
            browseStateFlow.value = browseState.copy(isLoading = true)
            delay(2000)
            browseStateFlow.value = browseState.copy(
                isLoading = false,
                books = listOf(
                    Book("Book 1", "Description 1"),
                    Book("Book 2", "Description 2")
                )
            )
        }
    }
}