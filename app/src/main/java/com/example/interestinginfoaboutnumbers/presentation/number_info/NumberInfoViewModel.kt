package com.example.interestinginfoaboutnumbers.presentation.number_info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interestinginfoaboutnumbers.core.Constants
import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.core.utils.UIEvent
import com.example.interestinginfoaboutnumbers.domain.use_case.GetNumberByIdUseCase
import com.example.interestinginfoaboutnumbers.presentation.number_info.components.NumberInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NumberInfoViewModel @Inject constructor(
    private val getNumberByIdUseCase: GetNumberByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG: String? = NumberInfoViewModel::class.simpleName

    private val _state = mutableStateOf(NumberInfoState())
    val state: State<NumberInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<String>(Constants.NUMBER_ID)?.let { numberId ->
            getNumberInfo(numberId.toInt())
        }
    }

    fun getNumberInfo(id: Int) {

        getNumberByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = NumberInfoState(numberInfo = result.data)
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Unknown error"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}