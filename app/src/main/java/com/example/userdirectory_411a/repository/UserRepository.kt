package com.example.userdirectory_411a.repository

import com.example.userdirectory_411a.api.RetrofitInstance
import com.example.userdirectory_411a.data.User
import com.example.userdirectory_411a.data.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun fetchAndStoreUsers() {
        try {
            val users = RetrofitInstance.api.getUsers()
            userDao.insertUsers(users)
        } catch (e: Exception) {
            print(e)
        }
    }
}