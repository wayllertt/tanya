package com.example.playlist_maker_android_nadtochievatatyana.data.storage

import android.content.Context
import androidx.room.Room
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.AppDatabase
import kotlin.jvm.java

object DatabaseProvider {
    @Volatile
    private var database: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlist_maker.db",
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { createdDatabase ->
                database = createdDatabase
            }
        }
    }
}
