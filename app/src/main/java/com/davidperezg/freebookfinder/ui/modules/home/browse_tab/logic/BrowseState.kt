package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic

import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.BookResult

data class SnackbarInteraction(
    val message: String,
    val action: String? = null,
    val onActionPerformed: () -> Unit = {},
    val onActionDismissed: () -> Unit = {},
    var hasBeenShownInTheUi: Boolean = false
)

data class BrowseState(
    var isLoading: Boolean = false,
    var books: List<BookResult> = emptyList(),
    var snackbarInteraction: SnackbarInteraction? = null
)