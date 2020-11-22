package com.tushar.tddandroid.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.tushar.tddandroid.adapters.ImageAdapter
import com.tushar.tddandroid.adapters.ShoppingItemAdapter
import com.tushar.tddandroid.repository.FakeShoppingRepository
import javax.inject.Inject

class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter,
                ShoppingViewModel(FakeShoppingRepository())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}