package com.tushar.tddandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.R
import com.tushar.tddandroid.adapters.ShoppingItemAdapter
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.getOrAwaitValue
import com.tushar.tddandroid.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testFragmentFactory: TestShoppingFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun swipeShoppingItem_deleteItemInDb() {
        val shoppingItem = ShoppingItem(
        id = 1,
        name = "Banana",
        amount =  10,
        price = 10.0f,
        imageUrl = "abc.com"
        )
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testViewModel = viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }
}