package com.anonymous.cars.fack

import com.anonymous.cars.domain.manufacture.model.ManufactureDomainModel
import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import com.anonymous.cars.presentation.manufacture.model.ManufactureUiModel

fun getManufactureDomainModel() {
    ManufactureDomainModel("123", "manufacture -1")
}

fun getFakeManufacturesDomainModel(): ManufacturesDomainModel {
    val list = mutableListOf<ManufactureDomainModel>()
    for (i in 1..15) {
        list.add(ManufactureDomainModel("$i", "manufactures -$i"))
    }

    return ManufacturesDomainModel(false,list)
}

fun getFakeManufacturesDomainModelOnLoadMore(): ManufacturesDomainModel {
    val list = mutableListOf<ManufactureDomainModel>()
    for (i in 1..30) {
        list.add(ManufactureDomainModel("$i", "manufactures -$i"))
    }

    return ManufacturesDomainModel(true,list)
}

fun getFakeManufacturesUIModel(): List<ManufactureUiModel> {
    val list = mutableListOf<ManufactureUiModel>()
    for (i in 1..15) {
        list.add(ManufactureUiModel("$i", "manufactures -$i"))
    }
    return list
}

fun getFakeManufacturesUIModelLoadMore(): List<ManufactureUiModel> {
    val list = mutableListOf<ManufactureUiModel>()
    for (i in 1..30) {
        list.add(ManufactureUiModel("$i", "manufactures -$i"))
    }
    return list
}
