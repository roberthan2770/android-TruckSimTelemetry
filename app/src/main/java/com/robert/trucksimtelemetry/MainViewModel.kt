package com.robert.trucksimtelemetry

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.trucksimtelemetry.dagger.SharedPreferencesModule.Companion.KEY_SERVER_IP
import com.robert.trucksimtelemetry.data.CoroutinesDispatcherProvider
import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.main.model.Truck
import com.robert.trucksimtelemetry.domain.GetTruckTelemetryUseCase
import com.robert.trucksimtelemetry.util.event.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

class MainViewModel @Inject constructor(
    private val getTruckTelemetryUseCase: GetTruckTelemetryUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    var realtimeTelemetryTimer: Timer? = null

    private val _uiModel = MutableLiveData<MainUiModel>()
    val uiModel: LiveData<MainUiModel>
        get() = _uiModel
    private val _uiVersionModel = MutableLiveData<UiVersionModel>()
    val uiVersionModel: LiveData<UiVersionModel>
        get() = _uiVersionModel


    fun getTruckTelemetry() =
        viewModelScope.launch(dispatcherProvider.computation) {
            val result = getTruckTelemetryUseCase()
            withContext(dispatcherProvider.main) {
                when (result) {
                    is Result.Success ->
                        emitUiModel(
                            truck = Event(result.data)
                        )

                    is Result.Error -> emitUiModel(
                        showError = Event(R.string.error_msg_query_failed)
                    )
                }
            }
        }

    fun startRealtimeTelemetry(interval: Long = 1000L) {
        if (realtimeTelemetryTimer == null) {
            realtimeTelemetryTimer = fixedRateTimer("Timer", false, 2000L, interval) {
                getTruckTelemetry()
            }
        }

    }

    fun setServerIP(serverIP: String) {
        sharedPreferences.edit().putString(KEY_SERVER_IP, serverIP).apply()
    }

    fun getServerIP(): String? {
        return sharedPreferences.getString(KEY_SERVER_IP, "")
    }

    private fun emitVersionModel(
        supportListVersion: String? = ""
    ) {
        _uiVersionModel.value = UiVersionModel(
            supportListVersion
        )
    }

    private fun emitUiModel(
        truck: Event<Truck>? = null,
        showError: Event<Int>? = null
    ) {
        _uiModel.value = MainUiModel(
            truck,
            showError
        )
    }
}

data class MainUiModel(
    val truck: Event<Truck>?,
    val showError: Event<Int>?
)

data class UiVersionModel(
    val supportListVersion: String?
)