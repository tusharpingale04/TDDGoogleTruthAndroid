package com.tushar.tddandroid

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.tushar.tddandroid.utils.CompareResource
import org.junit.After
import org.junit.Before

class CompareResourceTest{

    private lateinit var compareResource : CompareResource

    /**
     * Runs before every test
     */
    @Before
    fun setUp(){
        compareResource = CompareResource()
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