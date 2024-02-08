package com.example.interestinginfoaboutnumbers.presentation.numbers_list

import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.FlowCollector
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
                            Log.d(TAG, "onGetNumber Got the number ${number.toString()} with text ${result.data}")
                            numberUseCases.addNumberUseCase(Number(number = number, info = result.data.toString())).onEach { res ->
                                when(res) {
                                    is Resource.Success -> {
                                        Log.d(TAG, "onGetNumber SAVED!!")
                                    }
                                    else -> {Log.d(TAG, "onGetNumber NOT SAVED!!")}
                                }
                            }.launchIn(this)
                            Log.d(TAG, "Number is saved")
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

                        is Resource.Loading -> {
                            _eventFlow.emit(
                                UIEvent.ShowProgressBar
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
                        numberUseCases.addNumberUseCase(Number(number = num!!, info = result.data.toString())).onEach { res ->
                            Log.d(TAG, "onGetRandomNumber HERE SAVING!!")
                        when(res) {
                            is Resource.Success -> {
                                Log.d(TAG, "onGetRandomNumber SAVED!!, ${res.data}")
                            }
                            else -> {Log.d(TAG, "onGetRandomNumber NOT SAVED!!")}
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
        Log.d(TAG, "getNumbers HERE!")
        getNumberJob = numberUseCases.getAllNumbersUseCase()
            .onEach { numbers ->
                Log.d(TAG, "getNumbers HERE ${numbers.size}")
            _state.value = state.value.copy(
                numberListItems = numbers
            )
        }.launchIn(viewModelScope)
    }
}