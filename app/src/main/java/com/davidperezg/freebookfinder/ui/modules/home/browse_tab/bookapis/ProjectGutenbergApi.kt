package com.davidperezg.freebookfinder.ui.modules.home.browse_tab.bookapis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectGutenbergApi {
    @GET("api/book/")
    suspend fun search(
        @Query("search") query: String,
    ): Response<GutenbergApiSearchResponse>
}

data class GutenbergApiSearchResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<BookResult>
)

data class BookResult(
    val agents: List<Author>,
    val bookshelves: List<String>,
    val description: String?,
    val downloads: Int,
    val id: Int,
    val languages: List<String>,
    val license: String,
    val resources: List<Resource>,
    val subjects: List<String>,
    val title: String,
    val type: Any
)

data class Author(
    val id: Int,
    val person: String,
    val type: String
)

data class Resource(
    val id: Int,
    val type: String,
    val uri: String
)