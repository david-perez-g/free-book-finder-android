package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class Book(val title: String, val description: String)

class BrowseViewModel: ViewModel() {
    private val browseState = BrowseState()
    val browseStateFlow = MutableStateFlow(browseState)

    fun search(query: String) {
        viewModelScope.launch {
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