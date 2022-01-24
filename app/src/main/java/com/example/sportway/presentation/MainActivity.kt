package com.example.sportway.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.sportway.R
import com.example.sportway.common.ErrorCode
import com.example.sportway.databinding.ActivityMainBinding
import com.example.sportway.presentation.main.UiEventListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UiEventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        NavigationUI.setupActionBarWithNavController(this, navController)

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun showLoading() {
        binding.circularProgressIndicator.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        binding.circularProgressIndicator.visibility = View.INVISIBLE
    }

    override fun showError(errorCode: ErrorCode) {
        val errorMessageRes = when (errorCode) {
            ErrorCode.UNEXPECTED_ERROR -> R.string.unexpected_error
            ErrorCode.CONNECTION_ERROR -> R.string.connection_error
        }

        Snackbar.make(binding.root, errorMessageRes, BaseTransientBottomBar.LENGTH_LONG).apply {
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            view.background = ContextCompat.getDrawable(context, R.drawable.rounded_shape)
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.error_background))
        }.show()
    }

}