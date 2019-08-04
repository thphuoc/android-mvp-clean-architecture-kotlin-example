package com.android.data.local

import com.android.data.local.entities.CitySuggestionRoomEntity
import com.android.domain.entities.CitySuggestionEntity

fun CitySuggestionRoomEntity.toEntity() = CitySuggestionEntity(
    area = this.area,
    region = this.region,
    country = this.country
)

fun CitySuggestionEntity.toRoomEntity() = CitySuggestionRoomEntity(
    area = this.area,
    region = this.region,
    country = this.country
)
