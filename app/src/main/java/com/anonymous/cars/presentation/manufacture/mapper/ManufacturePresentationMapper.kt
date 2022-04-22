package com.anonymous.cars.presentation.manufacture.mapper

import com.anonymous.cars.domain.manufacture.model.ManufactureDomainModel
import com.anonymous.cars.presentation.manufacture.model.ManufactureUiModel

interface PresentationMapper<in I, out O> {
    fun convertTo(data: I?): O
}

class ManufacturePresentationMapper :
    PresentationMapper<List<ManufactureDomainModel>, List<ManufactureUiModel>> {
    override fun convertTo(data: List<ManufactureDomainModel>?): List<ManufactureUiModel> =
       data?.map {
           convert(it)
       }?: kotlin.run {
           emptyList()
       }

    private fun convert(domainModel:ManufactureDomainModel):ManufactureUiModel=
        ManufactureUiModel(domainModel.code,domainModel.name)
}
