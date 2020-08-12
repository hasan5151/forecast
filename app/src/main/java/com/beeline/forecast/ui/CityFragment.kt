package com.beeline.forecast.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beeline.forecast.R
import com.beeline.forecast.adapters.DeleteAdapter
import com.beeline.forecast.data.models.DeleteFactory
import com.beeline.forecast.data.models.DeleteModel
import com.beeline.forecast.viewmodel.ForecastVM
import kotlinx.android.synthetic.main.city_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Delete cities
 */
class CityFragment : Fragment() {
    private  var actionMode :ActionMode? =null
    private lateinit var cityAdapter : DeleteAdapter
    private lateinit var actionModeCallBack : ActionModeCallback
    private lateinit var placeholderText: TextView
    private lateinit var placeHolderIV: ImageView
    private lateinit var toolbar: Toolbar
    private val forecastVM : ForecastVM by sharedViewModel()
    var list  = arrayListOf<DeleteModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbar = view.rootView.findViewById(R.id.toolbar)
        placeholderText = view.findViewById(R.id.placeHolderText)
        placeHolderIV = view.findViewById(R.id.placeHolderIV)

        setToolbar()
        setAdapter()
        observeList()
    }

    private fun setToolbar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
        toolbar.title = context?.getString(R.string.deleteToolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionModeCallBack = ActionModeCallback()
    }

    private fun setAdapter() {
        cityAdapter = object : DeleteAdapter(list){
            override fun onItemClick(pos: Int?) {
                if (actionMode == null) {
                    actionMode = (activity as AppCompatActivity?)?.startSupportActionMode(actionModeCallBack)!!
                }
                val count: Int = cityAdapter.selectedItemCount()
                if (count == 0) {
                    actionMode?.finish()
                } else {
                    actionMode?.title = count.toString()
                    actionMode?.invalidate()
                }
            }
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = cityAdapter
    }

    private fun observeList() {
        forecastVM.recordedCities.observe(viewLifecycleOwner, Observer{
            list.clear()

            if (it.size==0){
                placeHolderIV.visibility = View.VISIBLE
                placeholderText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }else{
                placeHolderIV.visibility = View.INVISIBLE
                placeholderText.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
            }

            it.snapshot().forEach {
                list.add(DeleteFactory(it).turnDelete())
            }
            cityAdapter.notifyDataSetChanged()
        })
    }

    /**
     * change toolbar when select city
     */
    inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_native_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_delete -> {
                    cityAdapter.getSelectedItems().forEach {forecastVM.deleteCity(it)}
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            cityAdapter.clearAllSelections()
            actionMode = null
        }
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
}