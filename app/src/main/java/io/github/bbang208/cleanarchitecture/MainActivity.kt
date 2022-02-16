package io.github.bbang208.cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.bbang208.cleanarchitecture.databinding.ActivityMainBinding
import io.github.bbang208.cleanarchitecture.ui.common.TestAdapter
import io.github.bbang208.cleanarchitecture.util.PushEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.lifecycleOwner = this
        viewBinding.viewModel = this.viewModel

        val adapter = TestAdapter(appExecutors, this.viewModel)
        viewBinding.recyclerView.adapter = adapter


        viewModel.test.observe(this) { res ->
            Timber.e("status: ${res.status}")
            Timber.e("data: ${res.data?.time}")
            Timber.e("errorMessage: ${res.message}")
        }

        PushEvent.getInstance().observe(this) {
            Timber.e("event: $it")
        }

        val listener = { data1: Int, data2: String ->

        }

        PushEvent.getInstance().requestUpdate("asdad", listener)

        PushEvent.getInstance().requestUpdate("test", listener = { data1: Int, data2: String ->

        })
    }
}