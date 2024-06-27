package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.di

import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis.ProjectGutenbergApi
import com.davidperezg.freebookfinder.ui.modules.home.browse_tab.logic.BrowseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val browseModule = module {
    single<ProjectGutenbergApi> {
        Retrofit.Builder()
            .baseUrl("https://gnikdroy.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProjectGutenbergApi::class.java)
    }

    viewModel {
        BrowseViewModel(get())
    }
}