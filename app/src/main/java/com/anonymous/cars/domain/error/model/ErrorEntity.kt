package com.anonymous.cars.domain.error.model

sealed class ErrorEntity {
    object InvalidAccount : ErrorEntity()
}
