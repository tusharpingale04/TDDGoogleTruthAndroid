package com.tushar.tddandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.MainCoroutineRule
import com.tushar.tddandroid.getOrAwaitValue
import com.tushar.tddandroid.repository.FakeShoppingRepository
import com.tushar.tddandroid.utils.Constants
import com.tushar.tddandroid.utils.Status
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setUp(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, return error`(){
        viewModel.insertShoppingItem("Banana","","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, return error`(){
        val name = buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH + 1){
                append("b")
            }
        }
        viewModel.insertShoppingItem(name,"2","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, return error`(){
        val price = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH + 1){
                append(1)
            }
        }
        viewModel.insertShoppingItem("Banana","2",price)
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, return error`(){
        viewModel.insertShoppingItem("Banana","2222222222222222222","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid details, return success`(){
        viewModel.insertShoppingItem("Banana","2","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert shopping item with valid details and set image url empty, return success`(){
        viewModel.insertShoppingItem("Banana","2","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        val imageUrl = viewModel.currentImageUrl.getOrAwaitValue()
        assertThat(imageUrl).isEqualTo("")
    }

}