package com.tushar.tddandroid.repository

import androidx.lifecycle.LiveData
import com.tushar.tddandroid.data.local.ShoppingDao
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.data.remote.PixabayAPI
import com.tushar.tddandroid.data.remote.responses.ImageResponse
import com.tushar.tddandroid.utils.Resource
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val dao: ShoppingDao,
    private val api: PixabayAPI
): ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val result = api.searchForImage(imageQuery)
            if(result.isSuccessful) {
                result.body()?.let {
                    Resource.success(result.body())
                } ?: Resource.error("Something went wrong. Please try again later!", null)
            }else{
                Resource.error("Something went wrong. Please try again later!", null)
            }
        }catch (e: Exception){
            Resource.error("Something went wrong. Please try again later!", null)
        }
    }

}