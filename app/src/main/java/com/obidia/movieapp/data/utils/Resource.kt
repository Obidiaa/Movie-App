package com.obidia.movieapp.data.utils

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}