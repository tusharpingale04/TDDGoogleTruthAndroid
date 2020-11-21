package com.tushar.tddandroid.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(
                context,
                ShoppingItemDatabase::class.java
            ).allowMainThreadQueries()
            .build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "Banana",
            amount =  10,
            price = 10.0f,
            imageUrl = "abc.com"
        )
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            id = 1,
            name = "Banana",
            amount =  10,
            price = 10.0f,
            imageUrl = "abc.com"
        )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems.size).isEqualTo(0)
        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPrices() = runBlockingTest {
        val shoppingItem1 = ShoppingItem(
            id = 1,
            name = "Banana",
            amount =  10,
            price = 10.0f,
            imageUrl = "abc.com"
        )
        val shoppingItem2 = ShoppingItem(
            id = 2,
            name = "Banana",
            amount =  20,
            price = 10.0f,
            imageUrl = "abc.com"
        )
        val shoppingItem3 = ShoppingItem(
            id = 3,
            name = "Banana",
            amount =  30,
            price = 10.0f,
            imageUrl = "abc.com"
        )

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        val sum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(sum).isEqualTo(10*10.0f + 20*10.0f + 30*10.0f)
    }

}