package com.example.androidReferences.apps.loginApp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidReferences.apps.loginApp.data.models.User


@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile // ensure multiple threads access the resource correctly
        var INSTANCE: UsersDatabase? = null

        // responsible for creating and providing an instance of the UsersDatabase
        fun getDatabase(context: Context): UsersDatabase {
            return INSTANCE ?: synchronized(this) {
                // if null, initialize a new Room db, passing the context, db class, and db name
                val instance = Room.databaseBuilder(
                    context,
                    UsersDatabase::class.java,
                    "users_db"
                ).build()

                // assign to instance
                INSTANCE = instance
                return instance
            }
        }
    }
}
