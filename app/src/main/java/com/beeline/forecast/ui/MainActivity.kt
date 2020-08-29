package com.beeline.forecast.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.beeline.forecast.R
import com.beeline.forecast.utils.Prefs
import com.beeline.forecast.utils.WorkManagerUtil
import com.beeline.forecast.viewmodel.ForecastVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val prefs : Prefs by inject()
    private val forecastVM : ForecastVM by viewModel()
    private val workManagerUtil : WorkManagerUtil by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
     //   initDb()
        workManagerUtil.checkWorkManager()
        prefs.getDefault().registerOnSharedPreferenceChangeListener(this)
        forecastVM.getRecordedCities()
    }

    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title =""
        toolbar.bringToFront()
        setSupportActionBar(toolbar)

        val navController = findNavController(this,R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

/*    private fun initDb() {
        when(prefs.isInitial){
            true ->{
                println("start init")
                forecastVM.createDb()
                prefs.isInitial = false
            }
            false->{
                println("already init")
            }
        }
    }*/

    /**
     *  get changes from
     *  @see SettingsFragment
     */
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when(p1){
            "lastUpdate" ->{ workManagerUtil.scheduleWork() } 
            "degreeType" ->{ forecastVM.updateCities() }
        }
    }
}