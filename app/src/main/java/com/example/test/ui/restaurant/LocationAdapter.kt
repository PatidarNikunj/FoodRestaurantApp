package com.example.test.ui.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.test.R
import com.example.test.data.remote.model.response.location.LocationSuggestions
import java.util.*
import kotlin.collections.ArrayList


class LocationAdapter(
    context: Context,
    var resource: Int,
    var items: MutableList<LocationSuggestions>
) :
    ArrayAdapter<LocationSuggestions>(context, resource, items) {
    var suggestions: MutableList<LocationSuggestions> = ArrayList()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(resource, parent, false)
        }
        val locationDetails = items.get(position)
        val tvTitle: TextView = view!!.findViewById(R.id.tv_title)
        val tvCity: TextView = view.findViewById(R.id.tv_city)
        tvTitle.text = locationDetails.title
        tvCity.text = locationDetails.city_name
        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): LocationSuggestions? {
        return items.get(position)
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as LocationSuggestions).title!!
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                for (city in items) {
                    if (city.city_name?.toLowerCase(Locale.getDefault())
                            ?.contains(constraint.toString().toLowerCase(Locale.getDefault()))!!
                    ) {
                        suggestions.add(city)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults?
        ) {
            if (results != null && results.count > 0) {
                clear()
                for (city in results.values) {
                    add(city)
                    notifyDataSetChanged()
                }
            }
        }
    }
}

private operator fun Any.iterator(): Iterator<LocationSuggestions?> {
    return this.iterator()
}

private operator fun LocationSuggestions.iterator(): Iterator<LocationSuggestions> {
    return this.iterator()
}
