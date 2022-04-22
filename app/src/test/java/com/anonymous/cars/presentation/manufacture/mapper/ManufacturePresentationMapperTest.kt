package com.anonymous.cars.presentation.manufacture.mapper

import com.anonymous.cars.fack.getFakeManufacturesDomainModel
import com.google.common.truth.Truth
import org.junit.Test

class ManufacturePresentationMapperTest {
    @Test
    fun `success case for manufacture domain to UI model`(){
        val result =ManufacturePresentationMapper().convertTo(getFakeManufacturesDomainModel().manufactures)
        Truth.assertThat(result.size).isEqualTo(15)
        Truth.assertThat(result[0].code).containsMatch(getFakeManufacturesDomainModel().manufactures[0].code)
        Truth.assertThat(result[0].name).containsMatch(getFakeManufacturesDomainModel().manufactures[0].name)
    }
}
