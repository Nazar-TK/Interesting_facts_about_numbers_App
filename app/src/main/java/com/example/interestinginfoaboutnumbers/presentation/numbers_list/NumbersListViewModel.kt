package com.example.interestinginfoaboutnumbers.presentation.numbers_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interestinginfoaboutnumbers.core.utils.Resource
import com.example.interestinginfoaboutnumbers.core.utils.UIEvent
import com.example.interestinginfoaboutnumbers.presentation.numbers_list.components.NumberListState
import com.example.interestinginfoaboutnumbers.domain.model.Number
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

    private val TAG: String? = NumbersListViewModel::class.simpleName

    private val _searchQuery: MutableState<Int?> = mutableStateOf(null)
    var searchQuery: State<Int?> = _searchQuery

    private val _state = mutableStateOf(NumberListState())
    val state: State<NumberListState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getNumberJob: Job? = null

    init {
        getNumbers()
    }

    fun onNumberChanged(num: Int?) {
        _searchQuery.value = num
    }

    fun onGetNumber() {
        getNumberJob?.cancel()
        getNumberJob = viewModelScope.launch {
            val number = _searchQuery.value
            if (number == null) {
                _eventFlow.emit(
                    UIEvent.ShowSnackBar(
                        "Field can not be empty or contain symbols!"
                    )
                )
                return@launch
            }
            numberUseCases.getNumberUseCase(number).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d(
                            TAG,
                            "onGetNumber Got the number ${number.toString()} with text ${result.data}"
                        )
                        numberUseCases.addNumberUseCase(
                            Number(
                                number = number,
                                info = result.data.toString()
                            )
                        ).onEach { res ->
                            when (res) {
                                is Resource.Success -> {
                                    Log.d(TAG, "onGetNumber saved!")
                                }

                                else -> {
                                    Log.d(TAG, "onGetNumber not saved!")
                                }
                            }
                        }.launchIn(this)
                        getNumbers()
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "Error")
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun onGetRandomNumber() {
        getNumberJob?.cancel()
        getNumberJob = viewModelScope.launch {
            delay(500L)
            val number = _searchQuery.value
            numberUseCases.getRandomNumberUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val regex = Regex("\\b\\d+\\b")
                        val match = result.data?.let { regex.find(it) }
                        val num = match?.value?.toInt()
                        Log.d(TAG, "Got the number ${num.toString()} with text ${result.data}")
                        numberUseCases.addNumberUseCase(
                            Number(
                                number = num!!,
                                info = result.data.toString()
                            )
                        ).onEach { res ->
                            Log.d(TAG, "onGetRandomNumber HERE SAVING!!")
                            when (res) {
                                is Resource.Success -> {
                                    Log.d(TAG, "onGetRandomNumber SAVED!!, ${res.data}")
                                }

                                else -> {
                                    Log.d(TAG, "onGetRandomNumber NOT SAVED!!")
                                }
                            }
                        }.launchIn(this)
                        Log.d(TAG, "Number ${num.toString()} is saved")
                        getNumbers()
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "Error")
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
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
                    numberListItems = numbers
                )
            }.launchIn(viewModelScope)
    }
}