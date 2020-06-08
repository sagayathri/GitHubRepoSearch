package com.gayathriarumugam.github_task.Data.Network

import com.gayathriarumugam.github_task.Data.Model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("repositories")
    fun getRepos(
        @Query("q") searchString: String
    ): Call<SearchResponse>
}