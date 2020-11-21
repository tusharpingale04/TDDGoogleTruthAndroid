package com.tushar.tddandroid.utils

import android.content.Context

class ResourceComparator {

    fun isResourceMatching(context: Context, resId: Int, string: String): Boolean{
        return context.getString(resId) == string
    }
}