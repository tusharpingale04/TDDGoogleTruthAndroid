package com.tushar.tddandroid.utils

object Validator {

    private val userNames = listOf("Pankaj","Sunny","Suraj")

    fun isUsernamePasswordValid(username: String?, password: String?, confirmPassword: String?) : Boolean{
        if(username.isNullOrEmpty()){
            return false
        }
        if(password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()){
            return false
        }
        if(username in userNames){
            return false
        }
        if(password != confirmPassword){
            return false
        }
        if(password.count { it.isDigit() } < 2){
            return false
        }
        if(password.count { !it.isLetterOrDigit() } < 1){
            return false
        }
        return true
    }
}