package com.tushar.tddandroid.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.data.remote.responses.ImageResponse
import com.tushar.tddandroid.repository.ShoppingRepository
import com.tushar.tddandroid.utils.Constants
import com.tushar.tddandroid.utils.Event
import com.tushar.tddandroid.utils.Resource
import kotlinx.coroutines.launch

open class ShoppingViewModel @ViewModelInject constructor(
        private val repository: ShoppingRepository
): ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()
    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images : LiveData<Event<Resource<ImageResponse>>>
        get() = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl : LiveData<String>
        get() = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : LiveData<Event<Resource<ShoppingItem>>>
        get() = _insertShoppingItemStatus

    fun setCurrentImageUrl(url: String){
        _currentImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem){
        viewModelScope.launch {
            repository.deleteShoppingItem(shoppingItem)
        }
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem){
        viewModelScope.launch {
            repository.insertShoppingItem(shoppingItem)
        }
    }

    fun insertShoppingItem(name: String, amountStr: String, price: String){
        if(name.isEmpty() || amountStr.isEmpty() || price.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The Fields must not be empty",null)))
            return
        }
        if(name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Max Name Length condition exceeds ${name.length}",null)))
            return
        }

        if(price.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Max Price Length condition exceeds ${price.length}",null)))
            return
        }
        val amount = try {
            amountStr.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount",null)))
            return
        }
        val shoppingItem = ShoppingItem(
                name = name,
                price = price.toFloat(),
                amount = amount,
                imageUrl = _currentImageUrl.value ?: ""
        )
        insertShoppingItemIntoDb(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImages(query: String){
        if(query.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(query)
            _images.value = Event(response)
        }
    }
}