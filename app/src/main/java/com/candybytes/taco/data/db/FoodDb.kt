package com.candybytes.taco.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food


/**
 * Main database description.
 */
@Database(
    entities = [
        Category::class,
        Food::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodDb : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}
