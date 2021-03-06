package io.github.bbang208.cleanarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bbang208.cleanarchitecture.data.models.Repo
import io.github.bbang208.cleanarchitecture.data.source.TestRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    testRepository: TestRepository
) : ViewModel() {

    val test = testRepository.getTime()
    private val data1 = Repo("asd", "asd2", "junseo", 1000)
    private val data2 = Repo("asd1", "asd21", "junseo1", 100330)
    private val data3 = Repo("asd2", "asd22", "junseo2", 10100)
    private val data4 = Repo("asd3", "asd23", "junseo3", 10200)
    private val list = arrayListOf(data1, data2, data3, data4)

    private val _testList = MutableLiveData<List<Repo>>().apply {
        value = list.toList()
    }

    val testList: LiveData<List<Repo>> = _testList

    fun test(item: Repo) {
        list.remove(item).run {
            _testList.value = list.toList()
        }
    }

    fun addData() {
        list.add(list.size, Repo("asd3", UUID.randomUUID().toString(), "junseo", 10200)).run {
            _testList.value = list.toList()
        }

    }

}