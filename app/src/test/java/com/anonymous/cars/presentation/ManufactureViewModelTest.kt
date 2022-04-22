package com.anonymous.cars.presentation


import com.anonymous.cars.domain.*
import com.anonymous.cars.domain.error.model.ErrorEntity
import com.anonymous.cars.domain.error.useCase.ErrorUseCase
import com.anonymous.cars.domain.manufacture.model.FetechType
import com.anonymous.cars.domain.manufacture.useCase.GetManufacturesUseCase
import com.anonymous.cars.domain.manufacture.model.ManufactureDomainModel
import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import com.anonymous.cars.fack.*
import com.anonymous.cars.presentation.manufacture.model.ManufactureUiModel
import com.anonymous.cars.presentation.manufacture.viewModel.ManufactureViewModel
import com.anonymous.cars.presentation.manufacture.mapper.PresentationMapper
import com.google.common.truth.Truth.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManufactureViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    lateinit var viewModel: ManufactureViewModel

    @MockK
    lateinit var useCase: GetManufacturesUseCase

    @MockK
    lateinit var mapper: PresentationMapper<List<ManufactureDomainModel>, List<ManufactureUiModel>>

    @MockK
    lateinit var errorUseCase: ErrorUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `success case get manufactures`() = runBlocking {
        //given
        givenManufacturesUseCaseSuccessResult()
        givenMapperResultOfMovieDomainToUi(getFakeManufacturesDomainModel())
        //actual
        initViewModel()
        //result
        assertThat(viewModel.manufacturesUIModels.value is UIState.Success).isTrue()
        assertThat(viewModel.hasPages).isTrue()
    }

    @Test
    fun `error case get manufactures`() = runBlocking {
        //given
        givenManufacturesUseCaseErrorResult()
        givenError()
        //actual
        initViewModel()
        //result
        assertThat(viewModel.manufacturesUIModels.value is UIState.Error).isTrue()
        assertThat(viewModel.hasPages).isTrue()
    }


    @Test
    fun `success case reach last page on load more manufactures`() = runBlocking {
        //given
        //first request
        givenManufacturesUseCaseSuccessResult()
        givenMapperResultOfMovieDomainToUi(getFakeManufacturesDomainModel())
        initViewModel()
        // load more and reaches the last page
        givenManufacturesUseCaseSuccessResultOnLoadMore(getFakeManufacturesDomainModelOnLoadMore())
        givenMapperResultOfMovieDomainToUiLoadMore(getFakeManufacturesDomainModelOnLoadMore())
        viewModel.loadMoreBasedOnPageAvailable()
        //actual
        viewModel.loadMoreBasedOnPageAvailable()
        //result
        assertThat(viewModel.hasPages).isFalse()
        assertThat(viewModel.manufacturesUIModels.value is UIState.Success).isTrue()
        val items = viewModel.manufacturesUIModels.value as? UIState.Success<List<ManufactureUiModel>>
        assertThat(items?.data?.size).isEqualTo(30)
    }

    private fun initViewModel(){
        viewModel = ManufactureViewModel(useCase, mapper,errorUseCase)
    }

    private suspend fun givenManufacturesUseCaseSuccessResult() {
        coEvery {
            useCase.getManufactures()
        } returns flow {
            emit(ResultState.Success(getFakeManufacturesDomainModel()))
        }
    }

    private suspend fun givenManufacturesUseCaseSuccessResultOnLoadMore(list: ManufacturesDomainModel) {
        coEvery {
            useCase.getManufactures(FetechType.LOAD_MORE)
        } returns flow {
            emit(ResultState.Success(list))
        }
    }

    private  fun givenMapperResultOfMovieDomainToUi(data: ManufacturesDomainModel) {
        coEvery {
            mapper.convertTo(data.manufactures)
        } returns getFakeManufacturesUIModel()
    }

    private  fun givenMapperResultOfMovieDomainToUiLoadMore(data: ManufacturesDomainModel) {
        coEvery {
            mapper.convertTo(data.manufactures)
        } returns getFakeManufacturesUIModelLoadMore()
    }


    private suspend fun givenManufacturesUseCaseErrorResult() {
        coEvery {
            useCase.getManufactures()
        } returns flow {
            emit(
                ResultState.Error<ManufacturesDomainModel>
                    (ErrorEntity.InvalidAccount)
            )
        }
    }

    private fun givenError(){
        coEvery {
            errorUseCase.getErrorMessage(ErrorEntity.InvalidAccount)
        } returns ""
    }
}
