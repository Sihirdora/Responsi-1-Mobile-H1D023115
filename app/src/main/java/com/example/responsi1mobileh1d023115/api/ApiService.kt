package com.example.responsi1mobileh1d023115.api
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header
import com.example.responsi1mobileh1d023115.model.TeamResponse

interface ApiService {
    @GET("teams/{id}")
    suspend fun getTeamDetails(
        @Path("id") teamId: Int,
        @Header("X-Auth-Token") apiToken: String
    ): Response<TeamResponse>
}