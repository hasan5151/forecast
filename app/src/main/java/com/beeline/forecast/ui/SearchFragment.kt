package com.beeline.forecast.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beeline.forecast.R
import com.beeline.forecast.adapters.SearchAdapter
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.models.CityJson
import com.beeline.forecast.utils.AllCities
import com.beeline.forecast.viewmodel.ForecastVM
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

/**
 * Add new cities to the roomDb.
 */
class SearchFragment  : Fragment(), SearchView.OnQueryTextListener{
    private lateinit var searchAdapter : SearchAdapter
    private val forecastVM : ForecastVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val toolbar = view.rootView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        searchAdapter = object : SearchAdapter(AllCities().getAll()){
            override fun onClick(city: CityJson) {
                showDialog(city)
            }
        }

        forecastVM.state.observe(viewLifecycleOwner, Observer {
            if (it is Response.Success){
                if (it.data){
                    Toast.makeText(context,getString(R.string.city_added_tolist_successfully),Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(context,getString(R.string.city_already_added),Toast.LENGTH_LONG).show()
                }
            } else if (it is Response.Error){
                if (it.exception is UnknownHostException)
                    Toast.makeText(context,getString(R.string.city_couldnt_add_no_internet_connection),Toast.LENGTH_LONG).show()
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter= searchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView =
             searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.forecastFragment, true).build()
                Navigation.findNavController(requireView())
                    .navigate(R.id.forecastFragment, null, navOptions)
            }
        }

        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchAdapter.searchFilter().filter(newText)
        return true
    }

    fun showDialog(city: CityJson){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.addcity))
            .setMessage(resources.getString(R.string.city_question))
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                dialog.dismiss()
                forecastVM.createCity(city.id)
            }
            .show()
    }
}