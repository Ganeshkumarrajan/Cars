package com.anonymous.cars.domain.manufacture.useCase

import com.anonymous.cars.domain.manufacture.model.FetechType
import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import com.anonymous.cars.domain.ResultState
import com.anonymous.cars.domain.manufacture.ManufactureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetManufacturesUseCase {
    suspend fun getManufactures(fetchType: FetechType = FetechType.INTIAL_FETACH): Flow<ResultState<ManufacturesDomainModel>>
}

class GetManufactureUseCaseImpl(val repository: ManufactureRepository) : GetManufacturesUseCase {
    override suspend fun getManufactures(fetchType: FetechType): Flow<ResultState<ManufacturesDomainModel>> =
        flow {
            repository.getManufactures()
        }
}
