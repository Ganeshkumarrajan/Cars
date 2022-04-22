package com.anonymous.cars.presentation.manufacture.viewModel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.cars.domain.*
import com.anonymous.cars.presentation.UIState.Loading
import com.anonymous.cars.domain.error.model.ErrorEntity
import com.anonymous.cars.domain.error.useCase.ErrorUseCase
import com.anonymous.cars.domain.manufacture.model.FetechType
import com.anonymous.cars.domain.manufacture.useCase.GetManufacturesUseCase
import com.anonymous.cars.domain.manufacture.model.ManufactureDomainModel
import com.anonymous.cars.domain.manufacture.model.ManufacturesDomainModel
import com.anonymous.cars.presentation.UIState
import com.anonymous.cars.presentation.manufacture.model.ManufactureUiModel
import com.anonymous.cars.presentation.manufacture.mapper.PresentationMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ManufactureViewModel(
    private val useCase: GetManufacturesUseCase,
    private val mapper: PresentationMapper<List<ManufactureDomainModel>, List<ManufactureUiModel>>,
    private val errorUseCase: ErrorUseCase
) : ViewModel() {

    @VisibleForTesting
    var hasPages: Boolean = true
    private val _manufacturesUIModels = mutableStateOf<UIState<List<ManufactureUiModel>>>(Loading())
    val manufacturesUIModels: State<UIState<List<ManufactureUiModel>>> = _manufacturesUIModels

    init {
        manufactures(FetechType.INTIAL_FETACH)
    }

    fun loadMoreBasedOnPageAvailable(){
        if(!hasPages) return
        manufactures(FetechType.LOAD_MORE)
    }

    private fun manufactures(type: FetechType) =
        viewModelScope.launch {
            useCase.getManufactures(type).collectLatest {
                when (it) {
                    is ResultState.Success -> onSuccess(it.data)
                    is ResultState.Error -> onError(it.errorEntity)
                    else -> {}
                }
            }
        }

    private fun onSuccess(result: ManufacturesDomainModel) {
        hasPages = !result.isLastPage
        _manufacturesUIModels.value = UIState.Success(mapper.convertTo(result.manufactures))
    }

    private fun onError(errorEntity: ErrorEntity) {
        _manufacturesUIModels.value = UIState.Error(errorUseCase.getErrorMessage(errorEntity))
    }

}
