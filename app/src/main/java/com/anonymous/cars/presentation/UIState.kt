package com.anonymous.cars.presentation

sealed class UIState<T> {
    class Loading<T> : UIState<T>()
    data class Success<T>(val data:T): UIState<T>()
    data class Error<T>(val errorMessage:String): UIState<T>()
}
