package com.example.userdirectory_411a.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class DatabaseInstance : RoomDatabase() {
    abstract fun userDao(): UserDao
}

private var INSTANCE: DatabaseInstance? = null

fun getDatabase(context: Context): DatabaseInstance {
    return INSTANCE ?: Room.databaseBuilder(
        context.applicationContext,
        DatabaseInstance::class.java,
        "users"
    ).build().also { INSTANCE = it }
}