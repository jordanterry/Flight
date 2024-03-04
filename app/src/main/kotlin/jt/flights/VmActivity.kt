package jt.flights

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VmActivity : Activity() {
    private val viewModel = object : ViewModel() {
        fun start() {
            viewModelScope.launch {
                try {
                    throw RuntimeException("Hi!")
                } catch (exception: Exception) {
                    println("hello")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
    }
}