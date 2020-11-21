package com.tushar.tddandroid.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.R
import org.junit.After
import org.junit.Before

class ResourceComparatorTest{

    private lateinit var compareResource : ResourceComparator

    /**
     * Runs before every test
     */
    @Before
    fun setUp(){
        compareResource = ResourceComparator()
    }

    @Test
    fun stringResourceIsEqualToGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = compareResource.isResourceMatching(context, R.string.app_name, "TDDAndroid")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceIsNotEqualToGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = compareResource.isResourceMatching(context, R.string.app_name, "TusharApp")
        assertThat(result).isFalse()
    }

    @After
    fun tearDown(){
        //Clear Database
    }
}