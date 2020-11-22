package com.tushar.tddandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.R
import com.tushar.tddandroid.data.local.ShoppingItem
import com.tushar.tddandroid.getOrAwaitValue
import com.tushar.tddandroid.launchFragmentInHiltContainer
import com.tushar.tddandroid.repository.FakeShoppingRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Test
    fun clickShoppingImageButton_navigateToImagePickFragment(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.ivShoppingImage)).perform(click())
        Mockito.verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun clickBackPress_popBackStack(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        Mockito.verify(navController).popBackStack()

    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb(){
        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
        launchFragmentInHiltContainer<AddShoppingItemFragment> (fragmentFactory = fragmentFactory){
            viewModel = testViewModel
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Banana"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("10.0"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("10"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())
        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem(
                name = "Banana",
                price = 10.0f,
                amount = 10,
                imageUrl = ""
            ))
    }
}