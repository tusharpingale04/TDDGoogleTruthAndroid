package com.tushar.tddandroid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.R
import com.tushar.tddandroid.adapters.ImageAdapter
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
class ImagePickFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl(){
        val navController = Mockito.mock(NavController::class.java)
        val imageUrl = "testurl.com"
        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageUrl)
            viewModel = testViewModel
        }
        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)
    }

}