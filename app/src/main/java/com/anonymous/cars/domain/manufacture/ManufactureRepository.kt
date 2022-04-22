package com.anonymous.cars.domain.manufacture

import com.anonymous.cars.domain.ResultState
import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import kotlinx.coroutines.flow.Flow

interface ManufactureRepository {
    suspend fun getManufactures() : Flow<ResultState<ManufacturesDomainModel>>

}
