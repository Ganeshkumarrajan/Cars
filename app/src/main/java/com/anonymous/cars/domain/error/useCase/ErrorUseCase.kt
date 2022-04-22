package com.anonymous.cars.domain.error.useCase

import com.anonymous.cars.domain.error.model.ErrorEntity

interface ErrorUseCase {
    fun getErrorMessage(entity: ErrorEntity):String
}
