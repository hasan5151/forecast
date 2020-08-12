package com.beeline.forecast.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.beeline.forecast.R
import com.beeline.forecast.adapters.ForecastAdapter
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.viewmodel.ForecastVM
import kotlinx.android.synthetic.main.city_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Show weather of cities.
 */
class ForecastFragment : Fragment() {
   private val forecastVM : ForecastVM by sharedViewModel()
    private lateinit var recyclerView : RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var forecastAdapter : ForecastAdapter
    private lateinit var placeholderText: TextView
    private lateinit var placeHolderIV: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        placeholderText = view.findViewById(R.id.placeHolderText)
        placeHolderIV = view.findViewById(R.id.placeHolderIV)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh)

        setToolbar()
        setAdapter()
        swipe()
     }

    private fun setToolbar() {
        val toolbar = view?.rootView?.findViewById<Toolbar>(R.id.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar?.setBackgroundColor(ContextCompat.getColor(requireContext(),android.R.color.transparent))
        toolbar?.title = ""
    }

    private fun swipe() {
        forecastVM.state.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it is Response.Loading
        })

        swipeRefreshLayout.setOnRefreshListener {
            forecastVM.updateCities()
        }
    }

    private fun setAdapter() {
//        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        forecastAdapter = ForecastAdapter(requireContext())
        forecastVM.recordedCities.observe(viewLifecycleOwner, Observer {
            if (it.size==0){ //show placeholder remove recyclerview
                placeHolderIV.visibility = View.VISIBLE
                placeholderText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }else{ //hide placeholder show recyclerview
                placeHolderIV.visibility = View.INVISIBLE
                placeholderText.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            forecastAdapter.submitList(it)
        })
        recyclerView.adapter = forecastAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings ->{
                val action = ForecastFragmentDirections.actionForecastFragmentToSettingsFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

            R.id.search ->{
                val action = ForecastFragmentDirections.actionForecastFragmentToSearchFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

            R.id.delete->{
                val action = ForecastFragmentDirections.actionForecastFragmentToCityFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}