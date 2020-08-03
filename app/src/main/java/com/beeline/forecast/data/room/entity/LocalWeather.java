package com.beeline.forecast.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class LocalWeather {

    @PrimaryKey
    public Integer id;
    public String name;
    public Long lastUpdate;
    public Integer temp;
    public Integer feels_like;
    public String description;
    public String icon;

    public LocalWeather(Integer id, String name, Long lastUpdate, Integer temp, Integer feels_like, String description, String icon) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.temp = temp;
        this.feels_like = feels_like;
        this.description = description;
        this.icon = icon;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Integer feels_like) {
        this.feels_like = feels_like;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalWeather that = (LocalWeather) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastUpdate, that.lastUpdate) &&
                Objects.equals(feels_like, that.feels_like) &&
                Objects.equals(description, that.description) &&
                Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastUpdate, feels_like, description, icon);
    }
}