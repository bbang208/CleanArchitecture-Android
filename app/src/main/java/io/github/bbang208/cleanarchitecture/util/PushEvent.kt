package io.github.bbang208.cleanarchitecture.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PushEvent: LiveData<String>() {

    private val listener = { data: Int, data2: String ->
        postValue(data.toString())
        println("data2: $data2")
        /**
         * in main thread...
         */
        //value = price
    }


    override fun onActive() {
        super.onActive()
        requestUpdate("myUrl", listener)
    }

    override fun onInactive() {
        super.onInactive()
    }

    fun requestUpdate(url: String, listener: (code: Int, test: String) -> Unit) {
        println("url: $url")
        listener(123, "test22")


        CoroutineScope(Dispatchers.Default).launch {
            delay(3000)
            listener(123123, "test22")
        }
    }

    fun updateValue(data: String) {
        value = data
    }

    companion object {
        private lateinit var sInstance: PushEvent

        @MainThread
        fun getInstance(): PushEvent {
            sInstance = if (::sInstance.isInitialized) sInstance else PushEvent()
            return sInstance
        }
    }

}