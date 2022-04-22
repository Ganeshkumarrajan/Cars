package com.anonymous.cars.domain.manufacture.useCase

import com.anonymous.cars.domain.ResultState
import com.anonymous.cars.domain.manufacture.ManufactureRepository

import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import com.anonymous.cars.fack.CoroutineTestRule
import com.anonymous.cars.fack.getFakeManufacturesDomainModel

import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetManufactureUseCaseImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    lateinit var manufactureUseCaseImpl: GetManufactureUseCaseImpl

    @MockK
    lateinit var repository: ManufactureRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        manufactureUseCaseImpl = GetManufactureUseCaseImpl(repository)
    }

    @Test
   // fun `success case get manufactures`() = runBlocking {
    fun `success case get manufacture usecase`(): Unit = runBlocking {

        givenManufactureDomainFLowResult()

        val manufacturesFlow = manufactureUseCaseImpl.getManufactures()
        val result = manufacturesFlow.firstOrNull()

        Truth.assertThat(result is ResultState.Success)
    }

    private fun givenManufactureDomainFLowResult() {
        val flow = flow<ResultState<ManufacturesDomainModel>> {
            ResultState.Success(getFakeManufacturesDomainModel())
        }
        coEvery {
            repository.getManufactures()
        } returns flow
    }
}