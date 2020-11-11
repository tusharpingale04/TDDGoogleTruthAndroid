package com.tushar.tddandroid.utils

import android.content.Context

class CompareResource {

    fun isResourceMatching(context: Context, resId: Int, string: String): Boolean{
        return context.getString(resId) == string
    }
}