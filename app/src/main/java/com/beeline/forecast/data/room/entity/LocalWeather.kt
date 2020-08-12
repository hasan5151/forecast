package com.beeline.forecast.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class LocalWeather(
    @field:PrimaryKey var id: Int?,
    var name: String?,
    var lastUpdate: Long?,
    var temp: Int?,
    var feels_like: Int?,
    var description: String?,
    var icon: String?
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as LocalWeather
        return id == that.id &&
                name == that.name &&
                lastUpdate == that.lastUpdate &&
                feels_like == that.feels_like &&
                description == that.description &&
                icon == that.icon
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, lastUpdate, feels_like, description, icon)
    }
}