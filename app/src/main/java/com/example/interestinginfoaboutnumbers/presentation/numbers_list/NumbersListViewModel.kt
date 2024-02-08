package com.example.interestinginfoaboutnumbers.presentation.numbers_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.core.utils.UIEvent
import com.example.interestinginfoaboutnumbers.presentation.numbers_list.components.NumberListState
import com.example.interestinginfoaboutnumbers.domain.model.Number
import com.example.interestinginfoaboutnumbers.domain.use_case.AddNumberUseCase
import com.example.interestinginfoaboutnumbers.domain.use_case.NumberUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NumbersListViewModel @Inject constructor(
    private val numberUseCases: NumberUseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf(0)
    var searchQuery: State<Int> = _searchQuery

    private val _state = mutableStateOf(NumberListState())
    val state: State<NumberListState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getNumberJob: Job? = null

    init {
        getNumbers()
    }
    fun onNumberChanged(num: String) {
        viewModelScope.launch {
            if (num.isEmpty()) {
                _eventFlow.emit(
                    UIEvent.ShowSnackBar(
                        "Field can not be empty!"
                    )
                )
            } else if (!num.matches(Regex("-?\\d+"))) {
                _eventFlow.emit(
                    UIEvent.ShowSnackBar(
                        "There should be only numbers!"
                    )
                )
            } else {
                _searchQuery.value = num.toInt()
            }
        }
    }
    fun onGetNumber() {
        getNumberJob?.cancel()
        getNumberJob = viewModelScope.launch {
            delay(500L)
            val number = _searchQuery.value
            numberUseCases.getNumberUseCase(number).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            numberUseCases.addNumberUseCase(Number(number = number, info = result.data!!))
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _eventFlow.emit(
                                UIEvent.ShowProgressBar
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun getNumbers() {
        getNumberJob?.cancel()
        getNumberJob = numberUseCases.getAllNumbersUseCase()
            .onEach { numbers ->
                _state.value = state.value.copy(
                    numberListItems = numbers.data!!
                )
            }
            .launchIn(viewModelScope)
    }
}