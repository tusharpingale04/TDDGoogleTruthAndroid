package com.tushar.tddandroid.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.tushar.tddandroid.R
import com.tushar.tddandroid.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    private val requestManager: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeObservers()
        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                name = etShoppingItemName.text.toString(),
                amountStr = etShoppingItemAmount.text.toString(),
                price = etShoppingItemPrice.text.toString(),
            )
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeObservers(){
        viewModel.currentImageUrl.observe(viewLifecycleOwner, Observer {
            requestManager.load(it).into(ivShoppingImage)
        })

        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { resource ->
                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        Snackbar.make(requireActivity().rootLayout,
                            "Added Shopping Item",
                            Snackbar.LENGTH_LONG
                            ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireActivity().rootLayout,
                            resource.message ?: "Unknown Error Occurred!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

}