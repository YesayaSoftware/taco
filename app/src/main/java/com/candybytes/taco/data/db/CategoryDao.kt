package com.candybytes.taco.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.candybytes.taco.vo.Category

/**
 * Interface for database access for Category related operations.
 */
@Dao
interface CategoryDao {

    /**
     * Observes list of categories.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun observeCategories(): LiveData<List<Category>>

    /**
     * Insert a category in the database. If the category already exists, replace it.
     *
     * @param category the category to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(category: Category)

    /**
     * Select all categories from the categories table.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM categories")
    suspend fun getAllAsync(): List<Category>

    /**
     * Delete all categories.
     */
    @Query("DELETE FROM categories")
    suspend fun deleteCategories()

}
