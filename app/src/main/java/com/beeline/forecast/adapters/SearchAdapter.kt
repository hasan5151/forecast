package com.beeline.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.beeline.forecast.R
import com.beeline.forecast.data.models.CityJson

abstract class SearchAdapter(var city : ArrayList<CityJson>)  : RecyclerView.Adapter<SearchAdapter.SearchVH>() {
    val cityFilter = city.toList()

    class SearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.search_adapter,parent,false)
        return  SearchVH(view)
    }

    override fun getItemCount(): Int = city.size

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val cityPos = city[position]
        holder.textView.text = cityPos.name
        holder.cardView.setOnClickListener { onClick(cityPos) }
    }

    fun searchFilter() : Filter{
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val results = FilterResults()
                if (p0.isNullOrEmpty()) {
                    //No need for filter
                    city= ArrayList(cityFilter)
                    results.values = city;
                    results.count = city.size
                } else{
                    cityFilter.filter { it.name.toLowerCase().contains(p0) }.let {
                        results.values = it;
                        results.count = it.size
                    }
                }
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                city = p1?.values as ArrayList<CityJson>
                notifyDataSetChanged()
            }
        }
    }

    abstract fun onClick(city: CityJson)
}