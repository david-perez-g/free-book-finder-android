package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.di

import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic.BrowseViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val browseModule = module {
    singleOf(::BrowseViewModel)
}