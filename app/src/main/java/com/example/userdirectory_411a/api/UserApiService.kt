package com.example.userdirectory_411a.api

import com.example.userdirectory_411a.data.User
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

}