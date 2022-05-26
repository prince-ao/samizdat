package com.example.samizdat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(FavBook::class)], version = 3)
abstract class FavBookRoomDatabase: RoomDatabase() {
    abstract fun favBookDao(): FavBookDao

    companion object {

        private var INSTANCE: FavBookRoomDatabase? = null

        internal fun getDatabase(context: Context): FavBookRoomDatabase? {
            if(INSTANCE == null){
                synchronized(FavBookRoomDatabase::class.java) {
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder<FavBookRoomDatabase>(
                            context.applicationContext, FavBookRoomDatabase::class.java,
                            "favBook_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE
        }
    }
}