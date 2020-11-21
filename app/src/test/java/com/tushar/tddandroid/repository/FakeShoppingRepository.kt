package com.tushar.tddandroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.data.remote.responses.ImageResponse
import com.tushar.tddandroid.utils.Resource

class FakeShoppingRepository: ShoppingRepository {

    private val shoppingList = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingList)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshShoppingList(){
        observableShoppingItems.postValue(shoppingList)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice() : Float{
        return shoppingList.sumByDouble { it.amount * it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
        refreshShoppingList()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
        refreshShoppingList()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        }else{
            Resource.success(ImageResponse(
                listOf(),
                0,
                0
            ))
        }
    }
}