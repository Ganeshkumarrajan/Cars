package com.anonymous.cars.domain

import com.anonymous.cars.domain.error.model.ErrorEntity

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val errorEntity: ErrorEntity) : ResultState<T>()
}
