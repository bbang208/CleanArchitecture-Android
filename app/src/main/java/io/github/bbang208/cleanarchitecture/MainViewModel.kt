package io.github.bbang208.cleanarchitecture

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bbang208.cleanarchitecture.data.source.TestRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val testRepository: TestRepository
) : ViewModel() {

    val test = testRepository.getTime()
}