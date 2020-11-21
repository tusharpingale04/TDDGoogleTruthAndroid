package com.tushar.tddandroid.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidatorTest{

    @Test
    fun `when all valid return true`(){
        val result = Validator.isUsernamePasswordValid("tushar","Tushar@123","Tushar@123")
        assertThat(result).isTrue()
    }

    @Test
    fun `when username is Empty return false`(){
        val result = Validator.isUsernamePasswordValid("","Tushar@123","Tushar@123")
        assertThat(result).isFalse()
    }

    @Test
    fun `when password is Empty return false`(){
        val result = Validator.isUsernamePasswordValid("tushar","","Tushar@123")
        assertThat(result).isFalse()
    }

    @Test
    fun `when confirm password is Empty return false`(){
        val result = Validator.isUsernamePasswordValid("tushar","Tushar@123","")
        assertThat(result).isFalse()
    }

    @Test
    fun `when username already taken return false`(){
        val result = Validator.isUsernamePasswordValid("Sunny","Tushar@123","Tushar@123")
        assertThat(result).isFalse()
    }

    @Test
    fun `when password should contain at least 2 digits return false`(){
        val result = Validator.isUsernamePasswordValid("tushar","Tushar@3","Tushar@3")
        assertThat(result).isFalse()
    }

    @Test
    fun `when password is not equal to confirm password return false`(){
        val result = Validator.isUsernamePasswordValid("tushar","Tushar@13","Tushar@123")
        assertThat(result).isFalse()
    }

    @Test
    fun `when password doesn't contain special character return false`(){
        val result = Validator.isUsernamePasswordValid("tushar","Tushar123","Tushar123")
        assertThat(result).isFalse()
    }

    @Test
    fun fibonacciNumber(){
        var t1 = 0
        var t2 = 1
        val n = 10
        val list = mutableListOf<Int>()
        list.add(t1)
        list.add(t2)
        for(i in 1 until n){
            val sum  = t1 + t2
            t1 = t2
            t2 = sum
            list.add(sum)
        }
        print(list.toString())
    }

}