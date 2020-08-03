package com.beeline.forecast.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceFragmentCompat
import com.beeline.forecast.R

class SettingsFragment : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?,  rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setToolbar()
    }

    private fun setToolbar() {
        val toolbar = view?.rootView?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
        toolbar?.title = context?.getString(R.string.settings)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
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