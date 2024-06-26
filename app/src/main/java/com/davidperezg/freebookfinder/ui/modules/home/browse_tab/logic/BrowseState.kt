package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic

data class BrowseState(
    var isLoading: Boolean = false,
    var books: List<Book> = emptyList(),
)