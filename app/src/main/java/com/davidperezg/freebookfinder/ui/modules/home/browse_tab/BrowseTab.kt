package com.davidperezg.freebookfinder.ui.modules.home.browse_tab

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.di.browseModule
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic.BrowseViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.context.loadKoinModules

@Composable
fun BrowseTab(
    modifier: Modifier = Modifier,
    onNavigate: (route: String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    loadKoinModules(browseModule)
    val vm by viewModel<BrowseViewModel>()
    val state = vm.browseStateFlow.collectAsState()

    LaunchedEffect(state.value) {
        val snackbarInteraction = state.value.snackbarInteraction

        if (snackbarInteraction != null && !snackbarInteraction.hasBeenShownInTheUi) {
            val result = snackbarHostState.showSnackbar(
                message = snackbarInteraction.message,
                actionLabel = snackbarInteraction.action
            )

            if (result == SnackbarResult.ActionPerformed) {
                snackbarInteraction.onActionPerformed()
            } else {
                snackbarInteraction.onActionDismissed()
            }

            snackbarInteraction.hasBeenShownInTheUi = true
        }

    }

    Column(
        modifier = modifier
    ) {
        val text = remember {
            mutableStateOf("")
        }

        SearchBar(
            text = text,
            onValueChange = { query ->
                text.value = query
                vm.onQueryChange(query)
            },
            onNavigate = onNavigate
        )

        when {
            state.value.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            else -> BookSearchResults(books = state.value.books)
        }
    }
}
