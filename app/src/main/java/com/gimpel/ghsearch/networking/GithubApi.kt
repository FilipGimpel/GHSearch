package com.gimpel.ghsearch.networking

import com.gimpel.ghsearch.model.RepositorySearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories")
    suspend fun listRepos(@Query("q") searchQuery: String?): Response<RepositorySearchResult>
}