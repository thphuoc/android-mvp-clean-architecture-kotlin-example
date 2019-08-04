package com.android.domain.bus

import com.android.domain.entities.CitySuggestionEntity

/**
 * This class in order to store global data for the app
 */
data class AppBus(var citySelected: CitySuggestionEntity? = null)