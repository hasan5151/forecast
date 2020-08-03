package com.beeline.forecast.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beeline.forecast.R
import com.beeline.forecast.data.room.entity.LocalWeather
import java.text.SimpleDateFormat


open class ForecastAdapter(private val context : Context) : PagedListAdapter<LocalWeather, ForecastAdapter.ForecastVH>(DIFF_CALLBACK) {

    class ForecastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.city)
        val temp: TextView = itemView.findViewById(R.id.temp)
        val others: TextView = itemView.findViewById(R.id.others)
        val update: TextView = itemView.findViewById(R.id.update)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastVH {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_adapter, parent, false)
        return ForecastVH(view)
    }

    override fun onBindViewHolder(holder: ForecastVH, position: Int) {
        getItem(position)?.let {
            holder.city.text = it.name
            holder.temp.text = "${it.temp}°"
            holder.others.text = "${context.getString(R.string.feelslike)} ${it.feels_like}°"
            holder.description.text= it.description

            val formattedDate: String = SimpleDateFormat("dd MM yyyy HH:mm").format(it.lastUpdate)
            holder.update.text = "${context.getString(R.string.lastupdate)} $formattedDate"

            try {
                val id: Int = context.resources.getIdentifier(it.icon, "drawable", context.packageName)
                holder.imageView.setImageResource(id)
            }catch (e : Resources.NotFoundException){
                e.printStackTrace()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<LocalWeather> =
            object : DiffUtil.ItemCallback<LocalWeather>() {
                override fun areItemsTheSame(
                    oldItem: LocalWeather,
                    newItem: LocalWeather
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: LocalWeather,
                    newItem: LocalWeather
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}