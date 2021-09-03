package com.note11.schoolinfoapp.ui.screen.first.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.note11.schoolinfoapp.R
import com.note11.schoolinfoapp.databinding.ActivityWelcomeBinding
import com.note11.schoolinfoapp.ui.base.BaseActivity
import com.note11.schoolinfoapp.ui.screen.first.search.SearchActivity
import android.os.PowerManager
import android.provider.Settings


class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnWelcomeStart.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        batteryOptimizationPopup()
    }

    @SuppressLint("BatteryLife")
    private fun batteryOptimizationPopup() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager

        if (pm.isIgnoringBatteryOptimizations(packageName)) return

        Intent().also {
            it.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            it.data = Uri.parse("package:$packageName")
            startActivity(it)
        }
    }
}