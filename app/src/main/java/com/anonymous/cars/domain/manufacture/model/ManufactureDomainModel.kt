package com.anonymous.cars.domain.manufacture.model

data class ManufactureDomainModel(val code: String, val name: String)

data class ManufacturesDomainModel(
    val isLastPage: Boolean, val manufactures: List<ManufactureDomainModel>
)
