package com.tushar.tddandroid.repository

import androidx.lifecycle.LiveData
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.data.remote.responses.ImageResponse
import com.tushar.tddandroid.utils.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String) : Resource<ImageResponse>
}