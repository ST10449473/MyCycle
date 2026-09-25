package com.example.mycycle.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class UserRequest(
    val name: String = "",
    val email: String,
    val passwordHash: String
)

data class UserResponse(
    val message: String,
    val userId: Int,
    val name: String,
    val email: String
)

data class PeriodRequest(
    val userId: Int,
    val startDate: String,
    val endDate: String
)

data class PeriodResponse(
    val message: String,
    val periodId: Int
)

data class JournalRequest(
    val userId: Int,
    val symptoms: String,
    val mood: String,
    val entryDate: String
)

data class JournalResponse(
    val message: String,
    val journalId: Int
)

interface MyCycleApi {

    @POST("api/Users/register")
    suspend fun register(
        @Body user: UserRequest
    ): Response<UserResponse>

    @POST("api/Users/login")
    suspend fun login(
        @Body user: UserRequest
    ): Response<UserResponse>

    @DELETE("api/Users/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: Int
    ): Response<Unit>

    @POST("api/PeriodEntries")
    suspend fun createPeriod(
        @Body period: PeriodRequest
    ): Response<PeriodResponse>

    @GET("api/PeriodEntries/{userId}")
    suspend fun getPeriods(
        @Path("userId") userId: Int
    ): Response<List<PeriodRequest>>

    @POST("api/JournalEntries")
    suspend fun createJournal(
        @Body journal: JournalRequest
    ): Response<JournalResponse>

    @GET("api/JournalEntries/{userId}")
    suspend fun getJournalEntries(
        @Path("userId") userId: Int
    ): Response<List<JournalRequest>>
}