package edu.chapman.monsutauoka.services

import edu.chapman.monsutauoka.services.data.DataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class berriCounterService (val dataStore: DataStore) {
    private val key: String
    private var previousCount: Int? = null

    private val _berri: MutableStateFlow<Int>
    val berri: StateFlow<Int> get() = _berri

    init {
        // "StepCounterService.steps"
        key = "${this::class.simpleName}.${::berri.name}"

        var stepsValue = dataStore.load(key)?.toIntOrNull()
        _berri = MutableStateFlow(stepsValue ?: 0)
    }

    fun updateBerris(newCount: Int) {
        if (previousCount == null) {
            previousCount = newCount
            return
        }

        _berri.value += newCount - previousCount!!
        previousCount = newCount

        dataStore.save(key, _berri.value.toString())
    }

}