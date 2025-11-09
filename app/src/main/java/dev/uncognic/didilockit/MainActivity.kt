package dev.uncognic.didilockit // Make sure this package name matches yours

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.DynamicColors
import dev.uncognic.didilockit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivitiesIfAvailable(application)
        super.onCreate(savedInstanceState)

        // This is View Binding, the modern way to access views.
        // It replaces findViewById().
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // In Kotlin, 'val' is like 'final' in Java. 'var' is a mutable variable.
        val navView: BottomNavigationView = binding.navView

        // This finds the NavController from your NavHostFragment in activity_main.xml
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // This is the magic line!
        // It connects your BottomNavigationView with the NavController.
        // When you tap "Settings," the NavController will automatically handle
        // swapping the fragment to your SettingsFragment. [1]
        navView.setupWithNavController(navController)
    }
}
