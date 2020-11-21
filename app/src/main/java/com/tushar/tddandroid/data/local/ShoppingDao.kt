package com.tushar.tddandroid.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * from shopping_items")
    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(amount * price) from shopping_items")
    fun observeTotalPrice() : LiveData<Float>
}