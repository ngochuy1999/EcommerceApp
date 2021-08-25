package com.ngochuy.ecommerce.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDAO

    companion object {
        private var INSTANCE: CartDatabase?= null
        private val DB_NAME = "cart"

        fun getDatabase(context: Context): CartDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}